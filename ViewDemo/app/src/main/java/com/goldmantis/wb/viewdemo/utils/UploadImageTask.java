package com.goldmantis.wb.viewdemo.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.goldmantis.wb.viewdemo.activitys.SVProgressHUD;
import com.goldmantis.wb.viewdemo.adapter.QuickAdapter;
import com.goldmantis.wb.viewdemo.model.ModeBeen;
import com.goldmantis.wb.viewdemo.model.PictureBean;
import com.goldmantis.wb.viewdemo.model.PostPic;
import com.google.common.reflect.TypeToken;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.goldmantis.wb.viewdemo.common.Constants.compressBySizeHeight;
import static com.goldmantis.wb.viewdemo.common.Constants.compressBySizeWidth;


/**
 * Created by 00027450 on 2017/11/13.
 */

public class UploadImageTask extends AsyncTask<PictureBean,Integer,List<PictureBean>> {
    private static final String TAG = "UploadImageTask";
    private static final int TIME_OUT = 20*1000;   //超时时间
    private static final String CHARSET = "utf-8"; //设置编码
    private RecyclerView mRecyclerView;
    private QuickAdapter<PictureBean> mAdapter;
    private SVProgressHUD mSvProgressHUD;
    private String mUrl;
//    String url = "http://appservice.jtljia.com/appjia/v2/wkm/project/postpicture?projectId=" +projectId +"&userId=d2de46f1fd4949ddaa21955fb2c94e65";
    private int mDataSize;
    private List<PictureBean> resultList;
    private Context mContext;
    private Toast toast;

    public UploadImageTask(RecyclerView recyclerView, SVProgressHUD svProgressHUD, String url, int dataSize, Context context) {
        mRecyclerView = recyclerView;
        mAdapter = (QuickAdapter<PictureBean>) recyclerView.getAdapter();
        mSvProgressHUD = svProgressHUD;
        mUrl = url;
        mDataSize = dataSize;
        mContext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        resultList = new ArrayList<>();
        if (null != mSvProgressHUD) {
//            mSvProgressHUD.showWithMaskType(SVProgressHUD.SVProgressHUDMaskType.Clear);
        }
    }

    @Override
    protected List<PictureBean> doInBackground(PictureBean... params) {
        Log.v("iSpring", "DownloadTask -> doInBackground, Thread name: " + Thread.currentThread().getName());
        int position = 0;
        for(PictureBean bean: params){
            //遍历Url数组，依次下载对应的文件
//            Object[] result = downloadSingleFile(url);
//            int byteCount = (int)result[0];
//            totalByte += byteCount;
            //在下载完一个文件之后，我们就把阶段性的处理结果发布出去
//            publishProgress(result);
            //如果AsyncTask被调用了cancel()方法，那么任务取消，跳出for循环
            String resultResponse = uploadFile(bean.getFileUrl(),mUrl);
            TypeToken<ModeBeen<PostPic>> typeToken = new TypeToken<ModeBeen<PostPic>>() { };
            if (!TextUtils.isEmpty(resultResponse)) {
                ModeBeen<PostPic> response = GsonUtils.fromJson(resultResponse, typeToken);
                if ("1".equals(response.status)) {
                    PostPic postPic = response.data;
                    bean.setUrl(postPic.getUrl());
                    bean.setId(postPic.getId());
                    position ++;
                    if (params.length > 1) {
                        publishProgress(position);
                    }
                }else {
                    bean.setFileUrl("上传失败");
                    bean.setId("ERROR");
                }
            }else {
                bean.setFileUrl("网络出错，上传失败");
                bean.setId("ERROR");
            }
            resultList.add(bean);
            if(isCancelled()){
                break;
            }
        }
        //将总共下载的字节数作为结果返回
        return resultList;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        String numbers = values[0].toString();
        showMsgToast("已上传好"+numbers+"张图片");
    }

    @Override
    protected void onPostExecute(List<PictureBean> postionBeen) {
        super.onPostExecute(postionBeen);
        if (null != mSvProgressHUD) {
//            mSvProgressHUD.dismiss();
        }
        if (null == mAdapter){
            return;
        }
        List<PictureBean> datas = mAdapter.getListData();
        if (null == datas ){
            return;
        }
        if (null == postionBeen || postionBeen.size() < 1){
            showMsgToast("图片上传失败");
            return;
        }
        for (int i = 0; i < postionBeen.size(); i++) {
            PictureBean bean = postionBeen.get(i);
            if ("ERROR".equals(bean.getId())){
                if (bean.getPositionId() < mDataSize){
                    showMsgToast(bean.getPositionName()+"图片上传失败");
                }else {
                    showMsgToast("其他类型的图片有上传失败的");
                }
            }else {
                if (bean.getPositionId() < mDataSize){
                    PictureBean temp = datas.get(bean.getPositionId()-1);
                    temp.setId(bean.getId());
                    temp.setUrl(bean.getUrl());
                    temp.isSelector = true;
                    temp.isCloseShow = true;
                }else {
                    if (bean.getPositionId() == mDataSize+8){
                        PictureBean temp = datas.get(bean.getPositionId()-1);
                        temp.setId(bean.getId());
                        temp.setUrl(bean.getUrl());
                        temp.isSelector = true;
                        temp.isCloseShow = true;
                    }else {
                        bean.setPositionName("附照");
                        bean.isCloseShow = true;
                        bean.isSelector = true;
                        datas.add(datas.size()-1,bean);
                        PictureBean temp = datas.get(datas.size()-1);
                        temp.setPositionId(temp.getPositionId()+1);
                    }

                }
            }
        }
        mAdapter.notifyDataSetChanged();
        mRecyclerView.scrollToPosition(datas.size());
    }

    private String uploadFile(String filePath, String RequestURL)
    {
        String result = null;
        String BOUNDARY =  UUID.randomUUID().toString();  //边界标识   随机生成
        String PREFIX = "--" , LINE_END = "\r\n";
        String CONTENT_TYPE = "multipart/form-data";   //内容类型
        if (TextUtils.isEmpty(filePath)) {
            return result;
        }

        File file = new File(filePath);
        if (!file.exists()) {
            return result;
        }

        try {
            URL url = new URL(RequestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(TIME_OUT);
            conn.setConnectTimeout(TIME_OUT);
            conn.setDoInput(true);  //允许输入流
            conn.setDoOutput(true); //允许输出流
            conn.setUseCaches(false);  //不允许使用缓存
            conn.setRequestMethod("POST");  //请求方式
            conn.setRequestProperty("Charset", CHARSET);  //设置编码
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);


            if(file!=null)
            {
                /**
                 * 当文件不为空，把文件包装并且上传
                 */
                DataOutputStream dos = new DataOutputStream( conn.getOutputStream());
                StringBuffer sb = new StringBuffer();
                sb.append(PREFIX);
                sb.append(BOUNDARY);
                sb.append(LINE_END);
                /**
                 * 这里重点注意：
                 * name里面的值为服务器端需要key   只有这个key 才可以得到对应的文件
                 * filename是文件的名字，包含后缀名的   比如:abc.png
                 */

                sb.append("Content-Disposition: form-data; name=\"picture\"; filename=\""+file.getName()+"\""+LINE_END);
                sb.append("Content-Type: application/octet-stream; charset="+CHARSET+LINE_END);
                sb.append(LINE_END);
                dos.write(sb.toString().getBytes());
                Bitmap bitmap = BitmapUtil.readBitmapFromFileDescriptor(filePath, compressBySizeWidth,compressBySizeHeight);
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, dos);
//                byte[] baoBytes = baos.toByteArray();
//                ByteArrayInputStream isBm = new ByteArrayInputStream(baoBytes);
//                int len = 0;
//                byte[] bytes = new byte[1024];
//                while((len=isBm.read(bytes))!=-1)
//                {
//                    dos.write(bytes, 0, len);
//                }
//                isBm.close();
//                baos.close();
                dos.flush();
                dos.write(LINE_END.getBytes());
                byte[] end_data = (PREFIX+BOUNDARY+PREFIX+LINE_END).getBytes();
                dos.write(end_data);
                dos.flush();


                /**
                 * 获取响应码  200=成功
                 * 当响应成功，获取响应的流
                 */
                int res = conn.getResponseCode();
                Log.i(TAG, "response code:"+res);
                if(res==200){
                    Log.e(TAG, "request success");
                    InputStream input =  conn.getInputStream();
                    StringBuffer sb1= new StringBuffer();
                    int ss ;
                    while((ss=input.read())!=-1)
                    {
                        sb1.append((char)ss);
                    }
                    result = sb1.toString();
                    Log.i(TAG, "result : "+ result);
                }else{
                    Log.i(TAG, "request error");
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void showMsgToast(String msg) {
        if (null == toast) {
            toast = Toast.makeText(mContext, msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();

    }


}
