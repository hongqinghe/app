package com.example.webview;

import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class MainActivity extends AppCompatActivity {
  private WebView webView;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView= (WebView) findViewById(R.id.webView);
        handler=new Handler();
//        webView.loadUrl("http://www.baidu.com");
        webView.loadUrl("file:///android_asset/index.html");
        //设置属性在webView中显示  不打开新的浏览器
        webView.setWebViewClient(new WebViewClient(){
          //重写方法
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return  true;
            }
        });
        //与JavaScript交互


        //webView 参数设置
        WebSettings webSettings=webView.getSettings();
        webSettings.setSaveFormData(true);//保存表单数据
        webSettings.setSavePassword(true);//保存密码  在将来的wenView中将不再支持
        webSettings.setJavaScriptEnabled(true);//是否支持JavaScript
        webSettings.setSupportZoom(true);//是否支持缩放
        webView.goBack();//返回
        webView.goForward();//前进
        webView.requestFocus();//webView支持软键盘
     //处理标题弹窗
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            //处理标题
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }
             //处理图标
            @Override
            public void onReceivedIcon(WebView view, Bitmap icon) {
                super.onReceivedIcon(view, icon);
            }
        });
        webView.addJavascriptInterface(new MyObject(),"demo");

    }
    public class MyObject{
        //注解  表明是JavaScript中的方法
        //不添加注解的话就出现 has no method 'clickOnAndroid'", source: file:///android_asset/index.html错误
//        @JavascriptInterface
        public void clickOnAndroid(){
            handler.post(new Runnable() {
                @Override
                public void run() {
                    webView.loadUrl("javascript:myfun()");
                }
            });
        }
    }
    }

