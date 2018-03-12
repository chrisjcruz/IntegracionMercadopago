package prueba.com.ar.integracionmercadopago;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Application extends android.app.Application {

    public FirebaseUser getUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

}
