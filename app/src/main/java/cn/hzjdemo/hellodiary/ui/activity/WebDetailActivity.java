package cn.hzjdemo.hellodiary.ui.activity;

import android.graphics.Bitmap;
import android.os.Build;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import cn.hzjdemo.hellodiary.R;
import cn.hzjdemo.hellodiary.di.component.AppComponent;
import cn.hzjdemo.hellodiary.ui.base.BaseActivity;
import cn.hzjdemo.hellodiary.util.TitleBarBuilder;

/**
 * 网页详情
 */

public class WebDetailActivity extends BaseActivity {

    @BindView(R.id.web_view)
    WebView webView;
    @BindView(R.id.ll_root)
    LinearLayout llRoot;
    @BindView(R.id.title_text)
    TextView mTitleText;

    private TextView title;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_web_detail;
    }

    @Override
    public void initWidget() {
        initData();

        TitleBarBuilder titleBarBuilder = new TitleBarBuilder(this, llRoot);
        title = titleBarBuilder.getTitle();
        titleBarBuilder.setLeftIco(R.drawable.top_back)
                .setLeftIcoListening(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(false);
        ////设置自动加载图片
        webSettings.setLoadsImagesAutomatically(true);
        //设置此属性，可任意比例缩放
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);

        //原因是Android 5.0上Webview默认不允许加载Http与Https混合内容
        //解决https加载http内容有些图片加载不出来
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //两者都可以
            webSettings.setMixedContentMode(webSettings.getMixedContentMode());
            //mWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }


        //处理各种通知 & 请求事件
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //直接在当前webview加载，不调用系统浏览器
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
            }

            @Override
            public void onPageFinished(WebView view, String url) {
            }


        });

        //作用：辅助 WebView 处理 Javascript 的对话框,网站图标,网站标题等等
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                if (mTitleText != null) {
                    mTitleText.setText("获取标题：" + title);
                }
            }


            /**
             * 以下四个方法是与js交互相关方法
             * @param view
             * @param url
             * @param message
             * @param result
             * @return
             */
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                return super.onJsConfirm(view, url, message, result);
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                return super.onJsPrompt(view, url, message, defaultValue, result);
            }

            @Override
            public boolean onJsBeforeUnload(WebView view, String url, String message, JsResult result) {
                return super.onJsBeforeUnload(view, url, message, result);
            }
        });
    }

    protected void initData() {
        if (getIntent() != null) {
            if (getIntent().hasExtra("title")) {
                title.setText(getIntent().getStringExtra("title"));
            }
        }
        webView.loadUrl("https://www.baidu.com/");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void componentInject(AppComponent appComponent) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //销毁webview，释放内存
        if (webView != null) {
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();
            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
        }
    }
}