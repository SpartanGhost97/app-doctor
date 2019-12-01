package medical.login;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintStream;
import java.util.concurrent.Executor;

import medical.utils.DefaultCallback;
import medical.utils.NetworkConstants;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import medical.model.User;
import medical.model.LocalDataBase;

public class AgentLogin {

    private FirebaseAuth firebaseAuth;
    private Context context;
    private User user;
    private LocalDataBase localDataBase;

    private OkHttpClient okHttpClient;
    private GetPacientes getPacientes;
    private final String myTypeResponse = "response.body().string()";

    public AgentLogin(Context context) {
        firebaseAuth = FirebaseAuth.getInstance();
        this.context = context;
    }

    public void consultarPacientes() {
        getPacientes = new GetPacientes();
        getPacientes.execute();
    }

    public void registrar(final String email, final String password, final DefaultCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            try {
                                //saveUIDLogin();

                                okHttpClient = new OkHttpClient();

                                Request request = new Request.Builder()
                                        .url(NetworkConstants.URL + NetworkConstants.PATH_PROFILE)
                                        .get()
                                        .addHeader("id", FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .build();

                                Response response = okHttpClient.newCall(request).execute();

                                JSONObject json = new JSONObject(myTypeResponse);

                                if (response.code() == 200) {/*
                                    user.setEmail(json.getJSONObject("data").getString("email"));
                                    user.setNombre(json.getJSONObject("data").getString("nombre"));
                                    user.setId(json.getJSONObject("data").getString("id"));
                                    user.setTelefono(json.getJSONObject("data").getInt("telefono"));
                                    localDataBase.saveUser(user);*/

                                    callback.onFinishProcess(true, null);
                                } else
                                    callback.onFinishProcess(false, null);


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else
                            callback.onFinishProcess(false, null);
                    }
                });

            }
        }).start();

    }

    private class GetPacientes extends AsyncTask<String, Void, String> {

        public GetPacientes() {

        }

        @Override
        protected String doInBackground(String... strings) {

            System.out.println("peticion realizada");

            okHttpClient = new OkHttpClient();

            Request request = new Request.Builder().url(NetworkConstants.PATH_HELP).get().build();
            try {
                Response response = okHttpClient.newCall(request).execute();
                String respuesta = response.body().string();
                System.out.println("peticion realizada");
                System.out.println(respuesta);
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

    }

}
/*
    public void saveUIDLogin(String identified) {
        try {
            OutputStreamWriter osw = new OutputStreamWriter(openFileOutput("luid.bin", Activity.MODE_PRIVATE));
            osw.write(identified.trim());
            osw.flush();
            osw.close();
            Toast.makeText(getApplicationContext(), "Almacenando: " + identified, Toast.LENGTH_LONG).show();
        } catch (IOException ex) {
            Toast.makeText(getApplicationContext(), "Oops falla: " + ex, Toast.LENGTH_LONG).show();
        }
    }
*/