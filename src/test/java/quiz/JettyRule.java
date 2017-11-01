package quiz;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.SessionManager;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.junit.After;
import org.junit.rules.ExternalResource;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.EnumSet;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.http.HttpServlet;

public class JettyRule extends ExternalResource {

    private final int jettyPort = 32000 + (int) (Math.random() * 1000);

    private final Filter filter;
    private final HttpServlet servlet;
    private final String path;

    private Server jettyServer;
    private final SessionManager sessionManager = Mockito.mock(SessionManager.class);

    public JettyRule(HttpServlet servlet, String path) {
        this(null, servlet, path);
    }

    public JettyRule(Filter filter, HttpServlet servlet, String path) {
        this.filter = filter;
        this.servlet = servlet;
        this.path = path;
    }

    public void before() throws Exception {
        final ServletContextHandler handler = this.buildHandler();

        final Server server = new Server(this.jettyPort);
        server.setHandler(handler);
        server.start();

        this.jettyServer = server;
    }

    @After
    public void after() {
        if (this.jettyServer != null && this.jettyServer.isStarted()) {
            try {
                this.jettyServer.stop();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    protected ServletContextHandler buildHandler() throws Exception {
        final ServletContextHandler contextHandler = new ServletContextHandler();
        if (this.filter != null) {
            contextHandler.addFilter(new FilterHolder(this.filter), this.path, EnumSet.of(DispatcherType.REQUEST));
        }
        contextHandler.addServlet(new ServletHolder(this.servlet), this.path);
        contextHandler.setSessionHandler(new SessionHandler(this.getSessionManager()));

        return contextHandler;
    }

    public Response makeRequest(Request request) throws IOException {
        return new OkHttpClient().newCall(request).execute();
    }

    public String getUrl(String path) throws MalformedURLException {
        return this.jettyServer.getURI().toURL().toString().replaceAll("/$", "") + path;
    }

    public SessionManager getSessionManager() {
        return this.sessionManager;
    }
}