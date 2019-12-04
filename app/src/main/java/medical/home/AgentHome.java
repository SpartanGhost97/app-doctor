package medical.home;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import medical.utils.DefaultCallback;
import medical.utils.NetworkConstants;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AgentHome {


    private FirebaseAuth firebaseAuth;

    public void getPatientList(final String reason, final String name, final DefaultCallback notify) {

        firebaseAuth = FirebaseAuth.getInstance();
        final String uid = firebaseAuth.getUid();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient okhttp = new OkHttpClient.Builder()
                            .connectTimeout(15, TimeUnit.SECONDS)
                            .readTimeout(15, TimeUnit.SECONDS)
                            .build();

                    RequestBody body = new FormBody.Builder()
                            .add("id", uid)
                            .add("name", reason)
                            .build();

                    Request request = new Request.Builder()
                            .url(NetworkConstants.URL + NetworkConstants.PATH_HELP)
                            .post(body)
                            .build();

                    Response response = okhttp.newCall(request).execute();

                    if (response.code() == 200) {

                        JSONObject object = new JSONObject(response.body().string());
                        Log.i("Visaje: ",object.getString("pacientes"));


                        notify.onFinishProcess(true, "success");
                    } else {
                        notify.onFinishProcess(false, "Error intente nuevamente");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    notify.onFinishProcess(false, "Error en el servidor");
                }
            }
        }).start();
    }


}
