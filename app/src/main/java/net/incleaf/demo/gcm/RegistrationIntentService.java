package net.incleaf.demo.gcm;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by incleaf on 8/12/15.
 */
public class RegistrationIntentService extends IntentService {

    private static final String TAG = "RegistrationIntentService";
    private RestAdapter restAdapter;
    private APIService apiService;

    public RegistrationIntentService() {
        super(TAG);
    }

    /**
     * GCM을 위한 Instance ID의 토큰을 생성하여 가져온다.
     * @param intent
     */
    @SuppressLint("LongLogTag")
    @Override
    protected void onHandleIntent(Intent intent) {

        InstanceID instanceID = InstanceID.getInstance(this);
        String token = null;
        restAdapter = new RestAdapter.Builder()
                .setEndpoint(URLS.API_URL)
                .build();

        apiService = restAdapter.create(APIService.class);
        try {
            synchronized (TAG) {
                // GCM 앱을 등록하고 획득한 설정파일인 google-services.json을 기반으로 SenderID를 자동으로 가져온다.
                String default_senderId = getString(R.string.gcm_defaultSenderId);
                // GCM 기본 scope는 "GCM"이다.
                String scope = GoogleCloudMessaging.INSTANCE_ID_SCOPE;
                // Instance ID에 해당하는 토큰을 생성하여 가져온다.
                token = instanceID.getToken(default_senderId, scope, null);

                Log.i(TAG, "GCM Registration Token: " + token);
                post(token);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void post(String deviceToken){

        apiService.postDeviceToken(deviceToken, new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                Log.e("success", ""+response.getBody().toString());
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("fail", ""+error);
            }
        });
    }
}