import com.connecthub.factory.ConnectHubFactory;

public final class ConnectHubContext {
    private static final ConnectHubFactory FACTORY = new ConnectHubFactory();

    private ConnectHubContext() {
    }

    public static ConnectHubFactory factory() {
        return FACTORY;
    }
}
