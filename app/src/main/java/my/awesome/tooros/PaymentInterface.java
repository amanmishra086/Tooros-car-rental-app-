package my.awesome.tooros;

import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

public interface PaymentInterface {

    @JavascriptInterface
    public void success(String data);

    @JavascriptInterface
    public void error(String data);

}
