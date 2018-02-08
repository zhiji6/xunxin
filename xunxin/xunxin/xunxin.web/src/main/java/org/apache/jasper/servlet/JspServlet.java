//package org.apache.jasper.servlet;
//
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.lang.reflect.Constructor;
//import java.net.MalformedURLException;
//import java.security.AccessController;
//import java.security.PrivilegedActionException;
//import java.security.PrivilegedExceptionAction;
//
//import javax.servlet.ServletConfig;
//import javax.servlet.ServletContext;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.apache.jasper.Constants;
//import org.apache.jasper.EmbeddedServletOptions;
//import org.apache.jasper.Options;
//import org.apache.jasper.compiler.JspRuntimeContext;
//import org.apache.jasper.compiler.Localizer;
//import org.apache.jasper.runtime.ExceptionUtils;
//import org.apache.jasper.security.SecurityUtil;
//import org.apache.tomcat.PeriodicEventListener;
//
///**
// * Copyright © 2017 http://blog.csdn.net/noseparte © Like the wind, like rain
// * @Author Noseparte
// * @Compile 2017年12月7日 -- 下午4:34:36
// * @Version 1.0
// * @Description  JspServlet源码分析
// */
//public class JspServlet extends HttpServlet implements PeriodicEventListener {
//	
//	/**
//	 * @see transient修饰词释义
//	 * java语言的关键字，变量修饰符，如果用transient声明一个实例变量，
//	 * 当对象存储时，它的值不需要维持。换句话来说就是，用transient关键字标记的成员变量不参与序列化过程。
//	 */
//	private static final long serialVersionUID = 1L;
//    private final transient Log log = LogFactory.getLog(JspServlet.class);	
//    private transient ServletContext context;	//ServletContext对象包含在ServletConfig对象中，当servlet被初始化时，Web服务器提供servlet。
//    private ServletConfig config;  //servlet容器使用的servlet配置对象在初始化期间将信息传递给servlet。
//    private transient Options options; //一个类来保存所有与JSP引擎相关的init参数。
//    private transient JspRuntimeContext rctxt; //用于跟踪JSP编译时文件依赖项的类
//    private String jspFile;
//
//    /**
//     * javax.servlet.GenericServlet由servlet容器调用，以指示servlet正在被放置到服务中。
//     * 看到Servlet.init(javax.servlet.ServletConfig)。
//     * 该实现存储从servlet容器接收的ServletConfig对象，以供以后使用。
//     * 当覆盖该方法的这种形式时，调用super.init(config)。
//     * @param config
//     */
//    public void init(ServletConfig config) throws ServletException {
//        super.init(config);
//        this.config = config;
//        this.context = config.getServletContext();
//
//        String engineOptionsName = config.getInitParameter("engineOptionsClass");
//
//        if ((Constants.IS_SECURITY_ENABLED) && (engineOptionsName != null)) {
//            this.log.info(Localizer.getMessage("jsp.info.ignoreSetting",
//                    "engineOptionsClass", engineOptionsName));
//            engineOptionsName = null;
//        }
//
//        if (engineOptionsName != null) {
//            try {
//                ClassLoader loader = Thread.currentThread()
//                                           .getContextClassLoader();
//                Class<?> engineOptionsClass = loader.loadClass(engineOptionsName);
//                Class<?>[] ctorSig = { ServletConfig.class, ServletContext.class };
//                Constructor<?> ctor = engineOptionsClass.getConstructor(ctorSig);
//                Object[] args = { config, this.context };
//                this.options = ((Options) ctor.newInstance(args));
//            } catch (Throwable e) {
//                e = ExceptionUtils.unwrapInvocationTargetException(e);
//                ExceptionUtils.handleThrowable(e);
//
//                this.log.warn("Failed to load engineOptionsClass", e);
//
//                this.options = new EmbeddedServletOptions(config, this.context);
//            }
//        } else {
//            this.options = new EmbeddedServletOptions(config, this.context);
//        }
//
//        this.rctxt = new JspRuntimeContext(this.context, this.options);
//
//        if (config.getInitParameter("jspFile") != null) {
//            this.jspFile = config.getInitParameter("jspFile");
//
//            try {
//                if (null == this.context.getResource(this.jspFile)) {
//                    return;
//                }
//            } catch (MalformedURLException e) {
//                throw new ServletException("cannot locate jsp file", e);
//            }
//
//            try {
//                if (SecurityUtil.isPackageProtectionEnabled()) {
//                    AccessController.doPrivileged(new PrivilegedExceptionAction<Object>() {
//                            public Object run()
//                                throws IOException, ServletException {
//                                JspServlet.this.serviceJspFile(null, null,
//                                    JspServlet.this.jspFile, true);
//
//                                return null;
//                            }
//                        });
//                } else {
//                    serviceJspFile(null, null, this.jspFile, true);
//                }
//            } catch (IOException e) {
//                throw new ServletException("Could not precompile jsp: " +
//                    this.jspFile, e);
//            } catch (PrivilegedActionException e) {
//                Throwable t = e.getCause();
//
//                if ((t instanceof ServletException)) {
//                    throw ((ServletException) t);
//                }
//
//                throw new ServletException("Could not precompile jsp: " +
//                    this.jspFile, e);
//            }
//        }
//
//        if (this.log.isDebugEnabled()) {
//            this.log.debug(Localizer.getMessage("jsp.message.scratch.dir.is",
//                    this.options.getScratchDir().toString()));
//
//            this.log.debug(Localizer.getMessage(
//                    "jsp.message.dont.modify.servlets"));
//        }
//    }
//
//    /**
//     * 返回JspServletWrappers存在的jsp数量
//     * @return
//     */
//    public int getJspCount() {
//        return this.rctxt.getJspCount();
//    }
//
//    /**
//     * 重新设置JSP重新加载计数器。
//     * @param count
//     */
//    public void setJspReloadCount(int count) {
//        this.rctxt.setJspReloadCount(count);
//    }
//
//    /**
//     * 获取已重新加载的jsp的数量。
//     * @return
//     */
//    public int getJspReloadCount() {
//        return this.rctxt.getJspReloadCount();
//    }
//
//    /**
//     * 获得JSP限制队列中JSP的数量
//     * @return
//     */
//    public int getJspQueueLength() {
//        return this.rctxt.getJspQueueLength();
//    }
//
//    /**
//     * 获得已卸载的jsp的数量。
//     * @return
//     */
//    public int getJspUnloadCount() {
//        return this.rctxt.getJspUnloadCount();
//    }
//
//    boolean preCompile(HttpServletRequest request) throws ServletException {
//        String queryString = request.getQueryString();
//
//        if (queryString == null) {
//            return false;
//        }
//
//        int start = queryString.indexOf(Constants.PRECOMPILE);
//
//        if (start < 0) {
//            return false;
//        }
//
//        queryString = queryString.substring(start +
//                Constants.PRECOMPILE.length());
//
//        if (queryString.length() == 0) {
//            return true;
//        }
//
//        if (queryString.startsWith("&")) {
//            return true;
//        }
//
//        if (!queryString.startsWith("=")) {
//            return false;
//        }
//
//        int limit = queryString.length();
//        int ampersand = queryString.indexOf('&');
//
//        if (ampersand > 0) {
//            limit = ampersand;
//        }
//
//        String value = queryString.substring(1, limit);
//
//        if (value.equals("true")) {
//            return true;
//        }
//
//        if (value.equals("false")) {
//            return true;
//        }
//
//        throw new ServletException("Cannot have request parameter " +
//            Constants.PRECOMPILE + " set to " + value);
//    }
//
//    /**
//     * javax.servlet.http.HttpServlet从公共服务方法接收标准HTTP请求，并将它们分派到这个类中定义的doMethod方法。
//     * 该方法是servlet.service(javax.servlet的一个特定于http的版本。
//     * ServletRequest,javax.servlet.ServletResponse)方法。没有必要覆盖这个方法。
//     */
//    public void service(HttpServletRequest request, HttpServletResponse response)
//        throws ServletException, IOException {
//        String jspUri = this.jspFile;
//
//        if (jspUri == null) {
//            jspUri = (String) request.getAttribute(
//                    "javax.servlet.include.servlet_path");
//
//            if (jspUri != null) {
//                String pathInfo = (String) request.getAttribute(
//                        "javax.servlet.include.path_info");
//
//                if (pathInfo != null) {
//                    jspUri = jspUri + pathInfo;
//                }
//            } else {
//                jspUri = request.getServletPath();
//
//                String pathInfo = request.getPathInfo();
//
//                if (pathInfo != null) {
//                    jspUri = jspUri + pathInfo;
//                }
//            }
//        }
//
//        if (this.log.isDebugEnabled()) {
//            this.log.debug("JspEngine --> " + jspUri);
//            this.log.debug("\t     ServletPath: " + request.getServletPath());
//            this.log.debug("\t        PathInfo: " + request.getPathInfo());
//            this.log.debug("\t        RealPath: " +
//                this.context.getRealPath(jspUri));
//            this.log.debug("\t      RequestURI: " + request.getRequestURI());
//            this.log.debug("\t     QueryString: " + request.getQueryString());
//        }
//
//        try {
//            boolean precompile = preCompile(request);
//            serviceJspFile(request, response, jspUri, precompile);
//        } catch (RuntimeException e) {
//            throw e;
//        } catch (ServletException e) {
//            throw e;
//        } catch (IOException e) {
//            throw e;
//        } catch (Throwable e) {
//            ExceptionUtils.handleThrowable(e);
//            throw new ServletException(e);
//        }
//    }
//
//    /**
//     * javax.servlet.GenericServlet由servlet容器调用，
//     * 以指示servlet正在被从服务中取出/销毁
//     */
//    public void destroy() {
//        if (this.log.isDebugEnabled()) {
//            this.log.debug("JspServlet.destroy()");
//        }
//
//        this.rctxt.destroy();
//    }
//
//    /**
//     * 周期性的eventlistener,执行一个周期性任务，比如重新加载，等等。
//     */
//    public void periodicEvent() {
//        this.rctxt.checkUnload();
//        this.rctxt.checkCompile();
//    }
//
//    private void serviceJspFile(HttpServletRequest request,
//        HttpServletResponse response, String jspUri, boolean precompile)
//        throws ServletException, IOException {
//        JspServletWrapper wrapper = this.rctxt.getWrapper(jspUri);
//
//        if (wrapper == null) {
//            synchronized (this) {
//                wrapper = this.rctxt.getWrapper(jspUri);
//
//                if (wrapper == null) {
//                    if (null == this.context.getResource(jspUri)) {
//                        handleMissingResource(request, response, jspUri);
//
//                        return;
//                    }
//
//                    wrapper = new JspServletWrapper(this.config, this.options,
//                            jspUri, this.rctxt);
//
//                    this.rctxt.addWrapper(jspUri, wrapper);
//                }
//            }
//        }
//
//        try {
//            wrapper.service(request, response, precompile);
//        } catch (FileNotFoundException fnfe) {
//            handleMissingResource(request, response, jspUri);
//        }
//    }
//
//    private void handleMissingResource(HttpServletRequest request,
//        HttpServletResponse response, String jspUri)
//        throws ServletException, IOException {
//        String includeRequestUri = (String) request.getAttribute(
//                "javax.servlet.include.request_uri");
//
//        if (includeRequestUri != null) {
//            String msg = Localizer.getMessage("jsp.error.file.not.found", jspUri);
//
//            throw new ServletException(SecurityUtil.filter(msg));
//        }
//
//        try {
//            response.sendError(404, request.getRequestURI());
//        } catch (IllegalStateException ise) {
//            this.log.error(Localizer.getMessage("jsp.error.file.not.found",
//                    jspUri));
//        }
//    }
//}
