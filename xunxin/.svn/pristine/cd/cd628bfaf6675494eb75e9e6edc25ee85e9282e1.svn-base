package org.apache.catalina.servlets;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import javax.servlet.DispatcherType;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.ServletResponseWrapper;
import javax.servlet.UnavailableException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.catalina.Context;
import org.apache.catalina.Globals;
import org.apache.catalina.WebResource;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.connector.RequestFacade;
import org.apache.catalina.connector.ResponseFacade;
import org.apache.catalina.util.RequestUtil;
import org.apache.catalina.util.ServerInfo;
import org.apache.catalina.util.URLEncoder;
import org.apache.tomcat.util.res.StringManager;
import org.apache.tomcat.util.security.PrivilegedGetTccl;
import org.apache.tomcat.util.security.PrivilegedSetTccl;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.ext.EntityResolver2;

public class DefaultServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    protected static final StringManager sm = StringManager.getManager("org.apache.catalina.servlets");
    private static final DocumentBuilderFactory factory;
    private static final SecureEntityResolver secureEntityResolver;
    protected static final ArrayList < Range > FULL = new ArrayList();
    protected static final String mimeSeparation = "CATALINA_MIME_BOUNDARY";@Deprecated protected static final String RESOURCES_JNDI_NAME = "java:/comp/Resources";
    protected static final int BUFFER_SIZE = 4096;
    protected int debug;
    protected int input;
    protected boolean listings;
    protected boolean readOnly;
    protected CompressionFormat[] compressionFormats;
    protected int output;
    protected String localXsltFile;
    protected String contextXsltFile;
    protected String globalXsltFile;
    protected String readmeFile;
    protected transient WebResourceRoot resources;
    protected String fileEncoding;
    protected int sendfileSize;
    protected boolean useAcceptRanges;
    protected boolean showServerInfo;

    static {
        if (Globals.IS_SECURITY_ENABLED) {
            factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            factory.setValidating(false);
            secureEntityResolver = new SecureEntityResolver();
        } else {
            factory = null;
            secureEntityResolver = null;
        }
    }

    public DefaultServlet() {
        this.debug = 0;

        this.input = 2048;

        this.listings = false;

        this.readOnly = true;

        this.output = 2048;

        this.localXsltFile = null;

        this.contextXsltFile = null;

        this.globalXsltFile = null;

        this.readmeFile = null;

        this.resources = null;

        this.fileEncoding = null;

        this.sendfileSize = 49152;

        this.useAcceptRanges = true;

        this.showServerInfo = true;
    }

    public void init() throws ServletException {
        if (getServletConfig().getInitParameter("debug") != null) {
            this.debug = Integer.parseInt(getServletConfig().getInitParameter("debug"));
        }
        if (getServletConfig().getInitParameter("input") != null) {
            this.input = Integer.parseInt(getServletConfig().getInitParameter("input"));
        }
        if (getServletConfig().getInitParameter("output") != null) {
            this.output = Integer.parseInt(getServletConfig().getInitParameter("output"));
        }
        this.listings = Boolean.parseBoolean(getServletConfig().getInitParameter("listings"));
        if (getServletConfig().getInitParameter("readonly") != null) {
            this.readOnly = Boolean.parseBoolean(getServletConfig().getInitParameter("readonly"));
        }
        this.compressionFormats = parseCompressionFormats(getServletConfig().getInitParameter("precompressed"), getServletConfig().getInitParameter("gzip"));
        if (getServletConfig().getInitParameter("sendfileSize") != null) {
            this.sendfileSize = (Integer.parseInt(getServletConfig().getInitParameter("sendfileSize")) * 1024);
        }
        this.fileEncoding = getServletConfig().getInitParameter("fileEncoding");

        this.globalXsltFile = getServletConfig().getInitParameter("globalXsltFile");
        this.contextXsltFile = getServletConfig().getInitParameter("contextXsltFile");
        this.localXsltFile = getServletConfig().getInitParameter("localXsltFile");
        this.readmeFile = getServletConfig().getInitParameter("readmeFile");
        if (getServletConfig().getInitParameter("useAcceptRanges") != null) {
            this.useAcceptRanges = Boolean.parseBoolean(getServletConfig().getInitParameter("useAcceptRanges"));
        }
        if (this.input < 256) {
            this.input = 256;
        }
        if (this.output < 256) {
            this.output = 256;
        }
        if (this.debug > 0) {
            log("DefaultServlet.init:  input buffer size=" + this.input + ", output buffer size=" + this.output);
        }
        this.resources = ((WebResourceRoot) getServletContext().getAttribute("org.apache.catalina.resources"));
        if (this.resources == null) {
            throw new UnavailableException("No resources");
        }
        if (getServletConfig().getInitParameter("showServerInfo") != null) {
            this.showServerInfo = Boolean.parseBoolean(getServletConfig().getInitParameter("showServerInfo"));
        }
    }

    private CompressionFormat[] parseCompressionFormats(String precompressed, String gzip) {
        List < CompressionFormat > ret = new ArrayList();
        if ((precompressed != null) && (precompressed.indexOf('=') > 0)) {
            for (String pair: precompressed.split(",")) {
                String[] setting = pair.split("=");
                String encoding = setting[0];
                String extension = setting[1];
                ret.add(new CompressionFormat(extension, encoding));
            }
        } else if (precompressed != null) {
            if (Boolean.parseBoolean(precompressed)) {
                ret.add(new CompressionFormat(".br", "br"));
                ret.add(new CompressionFormat(".gz", "gzip"));
            }
        } else if (Boolean.parseBoolean(gzip)) {
            ret.add(new CompressionFormat(".gz", "gzip"));
        }
        return (CompressionFormat[]) ret.toArray(new CompressionFormat[ret.size()]);
    }

    protected String getRelativePath(HttpServletRequest request) {
        return getRelativePath(request, false);
    }

    protected String getRelativePath(HttpServletRequest request, boolean allowEmptyPath) {
        String servletPath;
        String pathInfo;
        if (request.getAttribute("javax.servlet.include.request_uri") != null) {
            pathInfo = (String) request.getAttribute("javax.servlet.include.path_info");
            servletPath = (String) request.getAttribute("javax.servlet.include.servlet_path");
        } else {
            pathInfo = request.getPathInfo();
            servletPath = request.getServletPath();
        }
        StringBuilder result = new StringBuilder();
        if (servletPath.length() > 0) {
            result.append(servletPath);
        }
        if (pathInfo != null) {
            result.append(pathInfo);
        }
        if ((result.length() == 0) && (!allowEmptyPath)) {
            result.append('/');
        }
        return result.toString();
    }

    protected String getPathPrefix(HttpServletRequest request) {
        return request.getContextPath();
    }

    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
    IOException {
        if (req.getDispatcherType() == DispatcherType.ERROR) {
            doGet(req, resp);
        } else {
            super.service(req, resp);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException,
    ServletException {
        serveResource(request, response, true, this.fileEncoding);
    }

    protected void doHead(HttpServletRequest request, HttpServletResponse response) throws IOException,
    ServletException {
        boolean serveContent = DispatcherType.INCLUDE.equals(request.getDispatcherType());
        serveResource(request, response, serveContent, this.fileEncoding);
    }

    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
    IOException {
        StringBuilder allow = new StringBuilder();

        allow.append("GET, HEAD");

        allow.append(", POST");

        allow.append(", PUT");

        allow.append(", DELETE");
        if (((req instanceof RequestFacade)) && (((RequestFacade) req).getAllowTrace())) {
            allow.append(", TRACE");
        }
        allow.append(", OPTIONS");

        resp.setHeader("Allow", allow.toString());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException,
    ServletException {
        doGet(request, response);
    }

    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
    IOException {
        if (this.readOnly) {
            resp.sendError(403);
            return;
        }
        String path = getRelativePath(req);

        WebResource resource = this.resources.getResource(path);

        Range range = parseContentRange(req, resp);

        InputStream resourceInputStream = null;
        try {
            if (range != null) {
                File contentFile = executePartialPut(req, range, path);
                resourceInputStream = new FileInputStream(contentFile);
            } else {
                resourceInputStream = req.getInputStream();
            }
            if (this.resources.write(path, resourceInputStream, true)) {
                if (resource.exists()) {
                    resp.setStatus(204);
                } else {
                    resp.setStatus(201);
                }
            } else {
                resp.sendError(409);
            }
            return;
        } finally {
            if (resourceInputStream != null) {
                try {
                    resourceInputStream.close();
                } catch(IOException localIOException1) {}
            }
        }
    }

    protected File executePartialPut(HttpServletRequest req, Range range, String path) throws IOException {
        File tempDir = (File) getServletContext().getAttribute("javax.servlet.context.tempdir");

        String convertedResourcePath = path.replace('/', '.');
        File contentFile = new File(tempDir, convertedResourcePath);
        if (contentFile.createNewFile()) {
            contentFile.deleteOnExit();
        }
        RandomAccessFile randAccessContentFile = new RandomAccessFile(contentFile, "rw");
        Throwable localThrowable4 = null;
        try {
            WebResource oldResource = this.resources.getResource(path);
            if (oldResource.isFile()) {
                BufferedInputStream bufOldRevStream = new BufferedInputStream(oldResource.getInputStream(), 4096);
                Throwable localThrowable5 = null;
                try {
                    byte[] copyBuffer = new byte['?'];
                    int numBytesRead;
                    while ((numBytesRead = bufOldRevStream.read(copyBuffer)) != -1) {
                        randAccessContentFile.write(copyBuffer, 0, numBytesRead);
                    }
                } catch(Throwable localThrowable1) {
                    localThrowable5 = localThrowable1;
                    throw localThrowable1;
                } finally {}
            }
            randAccessContentFile.setLength(range.length);

            randAccessContentFile.seek(range.start);

            byte[] transferBuffer = new byte['?'];
            BufferedInputStream requestBufInStream = new BufferedInputStream(req.getInputStream(), 4096);
            try {
                int numBytesRead;
                while ((numBytesRead = requestBufInStream.read(transferBuffer)) != -1) {
                    randAccessContentFile.write(transferBuffer, 0, numBytesRead);
                }
            } catch(Throwable localThrowable6) {
                throw localThrowable6;
            } finally {}
        } catch(Throwable localThrowable3) {
            localThrowable4 = localThrowable3;
            throw localThrowable3;
        } finally {
            if (randAccessContentFile != null) {
                if (localThrowable4 != null) {
                    try {
                        randAccessContentFile.close();
                    } catch(Throwable x2) {
                        localThrowable4.addSuppressed(x2);
                    }
                } else {
                    randAccessContentFile.close();
                }
            }
        }
        return contentFile;
    }

    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
    IOException {
        if (this.readOnly) {
            resp.sendError(403);
            return;
        }
        String path = getRelativePath(req);

        WebResource resource = this.resources.getResource(path);
        if (resource.exists()) {
            if (resource.delete()) {
                resp.setStatus(204);
            } else {
                resp.sendError(405);
            }
        } else {
            resp.sendError(404);
        }
    }

    protected boolean checkIfHeaders(HttpServletRequest request, HttpServletResponse response, WebResource resource) throws IOException {
        return (checkIfMatch(request, response, resource)) && (checkIfModifiedSince(request, response, resource)) && (checkIfNoneMatch(request, response, resource)) && (checkIfUnmodifiedSince(request, response, resource));
    }

    protected String rewriteUrl(String path) {
        return URLEncoder.DEFAULT.encode(path, StandardCharsets.UTF_8);
    }

    protected void serveResource(HttpServletRequest request, HttpServletResponse response, boolean content, String encoding) throws IOException,
    ServletException {
        boolean serveContent = content;

        String path = getRelativePath(request, true);
        if (this.debug > 0) {
            if (serveContent) {
                log("DefaultServlet.serveResource:  Serving resource '" + path + "' headers and data");
            } else {
                log("DefaultServlet.serveResource:  Serving resource '" + path + "' headers only");
            }
        }
        if (path.length() == 0) {
            doDirectoryRedirect(request, response);
            return;
        }
        WebResource resource = this.resources.getResource(path);
        boolean isError = DispatcherType.ERROR == request.getDispatcherType();
        if (!resource.exists()) {
            String requestUri = (String) request.getAttribute("javax.servlet.include.request_uri");
            if (requestUri == null) {
                requestUri = request.getRequestURI();
            } else {
                throw new FileNotFoundException(sm.getString("defaultServlet.missingResource", new Object[] {
                    requestUri
                }));
            }
            if (isError) {
                response.sendError(((Integer) request.getAttribute("javax.servlet.error.status_code")).intValue());
            } else {
                response.sendError(404, requestUri);
            }
            return;
        }
        if (!resource.canRead()) {
            String requestUri = (String) request.getAttribute("javax.servlet.include.request_uri");
            if (requestUri == null) {
                requestUri = request.getRequestURI();
            } else {
                throw new FileNotFoundException(sm.getString("defaultServlet.missingResource", new Object[] {
                    requestUri
                }));
            }
            if (isError) {
                response.sendError(((Integer) request.getAttribute("javax.servlet.error.status_code")).intValue());
            } else {
                response.sendError(403, requestUri);
            }
            return;
        }
        if ((resource.isFile()) && ((path.endsWith("/")) || (path.endsWith("\\")))) {
            String requestUri = (String) request.getAttribute("javax.servlet.include.request_uri");
            if (requestUri == null) {
                requestUri = request.getRequestURI();
            }
            response.sendError(404, requestUri);
            return;
        }
        boolean included = false;
        if (resource.isFile()) {
            included = request.getAttribute("javax.servlet.include.context_path") != null;
            if ((!included) && (!isError) && (!checkIfHeaders(request, response, resource))) {
                return;
            }
        }
        String contentType = resource.getMimeType();
        if (contentType == null) {
            contentType = getServletContext().getMimeType(resource.getName());
            resource.setMimeType(contentType);
        }
        String eTag = null;
        String lastModifiedHttp = null;
        if ((resource.isFile()) && (!isError)) {
            eTag = resource.getETag();
            lastModifiedHttp = resource.getLastModifiedHttp();
        }
        boolean usingPrecompressedVersion = false;
        if ((this.compressionFormats.length > 0) && (!included) && (resource.isFile()) && (!pathEndsWithCompressedExtension(path))) {
            List < PrecompressedResource > precompressedResources = getAvailablePrecompressedResources(path);
            if (!precompressedResources.isEmpty()) {
                Collection < String > varyHeaders = response.getHeaders("Vary");
                boolean addRequired = true;
                for (String varyHeader: varyHeaders) {
                    if (("*".equals(varyHeader)) || ("accept-encoding".equalsIgnoreCase(varyHeader))) {
                        addRequired = false;
                        break;
                    }
                }
                if (addRequired) {
                    response.addHeader("Vary", "accept-encoding");
                }
                PrecompressedResource bestResource = getBestPrecompressedResource(request, precompressedResources);
                if (bestResource != null) {
                    response.addHeader("Content-Encoding", bestResource.format.encoding);
                    resource = bestResource.resource;
                    usingPrecompressedVersion = true;
                }
            }
        }
        ArrayList < Range > ranges = null;
        long contentLength = -1L;
        if (resource.isDirectory()) {
            if (!path.endsWith("/")) {
                doDirectoryRedirect(request, response);
                return;
            }
            if (!this.listings) {
                response.sendError(404, request.getRequestURI());

                return;
            }
            contentType = "text/html;charset=UTF-8";
        } else {
            if (!isError) {
                if (this.useAcceptRanges) {
                    response.setHeader("Accept-Ranges", "bytes");
                }
                ranges = parseRange(request, response, resource);

                response.setHeader("ETag", eTag);

                response.setHeader("Last-Modified", lastModifiedHttp);
            }
            contentLength = resource.getContentLength();
            if (contentLength == 0L) {
                serveContent = false;
            }
        }
        ServletOutputStream ostream = null;
        PrintWriter writer = null;
        if (serveContent) {
            try {
                ostream = response.getOutputStream();
            } catch(IllegalStateException e) {
                if ((!usingPrecompressedVersion) && ((contentType == null) || (contentType.startsWith("text")) || (contentType.endsWith("xml")) || (contentType.contains("/javascript")))) {
                    writer = response.getWriter();

                    ranges = FULL;
                } else {
                    throw e;
                }
            }
        }
        ServletResponse r = response;
        long contentWritten = 0L;
        while ((r instanceof ServletResponseWrapper)) {
            r = ((ServletResponseWrapper) r).getResponse();
        }
        if ((r instanceof ResponseFacade)) {
            contentWritten = ((ResponseFacade) r).getContentWritten();
        }
        if (contentWritten > 0L) {
            ranges = FULL;
        }
        if ((resource.isDirectory()) || (isError) || (((ranges != null) && (!ranges.isEmpty())) || ((request.getHeader("Range") == null) || (ranges == FULL)))) {
            if (contentType != null) {
                if (this.debug > 0) {
                    log("DefaultServlet.serveFile:  contentType='" + contentType + "'");
                }
                response.setContentType(contentType);
            }
            if ((resource.isFile()) && (contentLength >= 0L) && ((!serveContent) || (ostream != null))) {
                if (this.debug > 0) {
                    log("DefaultServlet.serveFile:  contentLength=" + contentLength);
                }
                if (contentWritten == 0L) {
                    response.setContentLengthLong(contentLength);
                }
            }
            if (serveContent) {
                try {
                    response.setBufferSize(this.output);
                } catch(IllegalStateException localIllegalStateException1) {}
                InputStream renderResult = null;
                if (ostream == null) {
                    if (resource.isDirectory()) {
                        renderResult = render(getPathPrefix(request), resource, encoding);
                    } else {
                        renderResult = resource.getInputStream();
                    }
                    copy(renderResult, writer, encoding);
                } else {
                    if (resource.isDirectory()) {
                        renderResult = render(getPathPrefix(request), resource, encoding);
                    } else if (!checkSendfile(request, response, resource, contentLength, null)) {
                        byte[] resourceBody = resource.getContent();
                        if (resourceBody == null) {
                            renderResult = resource.getInputStream();
                        } else {
                            ostream.write(resourceBody);
                        }
                    }
                    if (renderResult != null) {
                        copy(renderResult, ostream);
                    }
                }
            }
        } else {
            if ((ranges == null) || (ranges.isEmpty())) {
                return;
            }
            response.setStatus(206);
            if (ranges.size() == 1) {
                Range range = (Range) ranges.get(0);
                response.addHeader("Content-Range", "bytes " + range.start + "-" + range.end + "/" + range.length);

                long length = range.end - range.start + 1L;
                response.setContentLengthLong(length);
                if (contentType != null) {
                    if (this.debug > 0) {
                        log("DefaultServlet.serveFile:  contentType='" + contentType + "'");
                    }
                    response.setContentType(contentType);
                }
                if (serveContent) {
                    try {
                        response.setBufferSize(this.output);
                    } catch(IllegalStateException localIllegalStateException2) {}
                    if (ostream != null) {
                        if (!checkSendfile(request, response, resource, range.end - range.start + 1L, range)) {
                            copy(resource, ostream, range);
                        }
                    } else {
                        throw new IllegalStateException();
                    }
                }
            } else {
                response.setContentType("multipart/byteranges; boundary=CATALINA_MIME_BOUNDARY");
                if (serveContent) {
                    try {
                        response.setBufferSize(this.output);
                    } catch(IllegalStateException localIllegalStateException3) {}
                    if (ostream != null) {
                        copy(resource, ostream, ranges.iterator(), contentType);
                    } else {
                        throw new IllegalStateException();
                    }
                }
            }
        }
    }

    private boolean pathEndsWithCompressedExtension(String path) {
        for (CompressionFormat format: this.compressionFormats) {
            if (path.endsWith(format.extension)) {
                return true;
            }
        }
        return false;
    }

    private List < PrecompressedResource > getAvailablePrecompressedResources(String path) {
        List < PrecompressedResource > ret = new ArrayList(this.compressionFormats.length);
        for (CompressionFormat format: this.compressionFormats) {
            WebResource precompressedResource = this.resources.getResource(path + format.extension);
            if ((precompressedResource.exists()) && (precompressedResource.isFile())) {
                ret.add(new PrecompressedResource(precompressedResource, format));
            }
        }
        return ret;
    }

    private PrecompressedResource getBestPrecompressedResource(HttpServletRequest request, List < PrecompressedResource > precompressedResources) {
        Enumeration < String > headers = request.getHeaders("Accept-Encoding");
        PrecompressedResource bestResource = null;
        double bestResourceQuality = 0.0D;
        int bestResourcePreference = Integer.MAX_VALUE;
        while (headers.hasMoreElements()) {
            String header = (String) headers.nextElement();
            for (String preference: header.split(",")) {
                double quality = 1.0D;
                int qualityIdx = preference.indexOf(';');
                if (qualityIdx > 0) {
                    int equalsIdx = preference.indexOf('=', qualityIdx + 1);
                    if (equalsIdx != -1) {
                        quality = Double.parseDouble(preference.substring(equalsIdx + 1).trim());
                    }
                } else if (quality >= bestResourceQuality) {
                    String encoding = preference;
                    if (qualityIdx > 0) {
                        encoding = encoding.substring(0, qualityIdx);
                    }
                    encoding = encoding.trim();
                    if ("identity".equals(encoding)) {
                        bestResource = null;
                        bestResourceQuality = quality;
                        bestResourcePreference = Integer.MAX_VALUE;
                    } else if ("*".equals(encoding)) {
                        bestResource = (PrecompressedResource) precompressedResources.get(0);
                        bestResourceQuality = quality;
                        bestResourcePreference = 0;
                    } else {
                        for (int i = 0; i < precompressedResources.size(); i++) {
                            PrecompressedResource resource = (PrecompressedResource) precompressedResources.get(i);
                            if (encoding.equals(resource.format.encoding)) {
                                if ((quality <= bestResourceQuality) && (i >= bestResourcePreference)) {
                                    break;
                                }
                                bestResource = resource;
                                bestResourceQuality = quality;
                                bestResourcePreference = i;
                                break;
                            }
                        }
                    }
                }
            }
        }
        return bestResource;
    }

    private void doDirectoryRedirect(HttpServletRequest request, HttpServletResponse response) throws IOException {
        StringBuilder location = new StringBuilder(request.getRequestURI());
        location.append('/');
        if (request.getQueryString() != null) {
            location.append('?');
            location.append(request.getQueryString());
        }
        response.sendRedirect(response.encodeRedirectURL(location.toString()));
    }

    protected Range parseContentRange(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String rangeHeader = request.getHeader("Content-Range");
        if (rangeHeader == null) {
            return null;
        }
        if (!rangeHeader.startsWith("bytes")) {
            response.sendError(400);
            return null;
        }
        rangeHeader = rangeHeader.substring(6).trim();

        int dashPos = rangeHeader.indexOf('-');
        int slashPos = rangeHeader.indexOf('/');
        if (dashPos == -1) {
            response.sendError(400);
            return null;
        }
        if (slashPos == -1) {
            response.sendError(400);
            return null;
        }
        Range range = new Range();
        try {
            range.start = Long.parseLong(rangeHeader.substring(0, dashPos));
            range.end = Long.parseLong(rangeHeader.substring(dashPos + 1, slashPos));

            range.length = Long.parseLong(rangeHeader.substring(slashPos + 1, rangeHeader.length()));
        } catch(NumberFormatException e) {
            response.sendError(400);
            return null;
        }
        if (!range.validate()) {
            response.sendError(400);
            return null;
        }
        return range;
    }

    protected ArrayList < Range > parseRange(HttpServletRequest request, HttpServletResponse response, WebResource resource) throws IOException {
        String headerValue = request.getHeader("If-Range");
        if (headerValue != null) {
            long headerValueTime = -1L;
            try {
                headerValueTime = request.getDateHeader("If-Range");
            } catch(IllegalArgumentException localIllegalArgumentException) {}
            String eTag = resource.getETag();
            long lastModified = resource.getLastModified();
            if (headerValueTime == -1L) {
                if (!eTag.equals(headerValue.trim())) {
                    return FULL;
                }
            } else if (lastModified > headerValueTime + 1000L) {
                return FULL;
            }
        }
        long fileLength = resource.getContentLength();
        if (fileLength == 0L) {
            return null;
        }
        String rangeHeader = request.getHeader("Range");
        if (rangeHeader == null) {
            return null;
        }
        if (!rangeHeader.startsWith("bytes")) {
            response.addHeader("Content-Range", "bytes */" + fileLength);
            response.sendError(416);

            return null;
        }
        rangeHeader = rangeHeader.substring(6);

        ArrayList < Range > result = new ArrayList();
        StringTokenizer commaTokenizer = new StringTokenizer(rangeHeader, ",");
        while (commaTokenizer.hasMoreTokens()) {
            String rangeDefinition = commaTokenizer.nextToken().trim();

            Range currentRange = new Range();
            currentRange.length = fileLength;

            int dashPos = rangeDefinition.indexOf('-');
            if (dashPos == -1) {
                response.addHeader("Content-Range", "bytes */" + fileLength);
                response.sendError(416);

                return null;
            }
            if (dashPos == 0) {
                try {
                    long offset = Long.parseLong(rangeDefinition);
                    currentRange.start = (fileLength + offset);
                    currentRange.end = (fileLength - 1L);
                } catch(NumberFormatException e) {
                    response.addHeader("Content-Range", "bytes */" + fileLength);

                    response.sendError(416);

                    return null;
                }
            } else {
                try {
                    currentRange.start = Long.parseLong(rangeDefinition.substring(0, dashPos));
                    if (dashPos < rangeDefinition.length() - 1) {
                        currentRange.end = Long.parseLong(rangeDefinition.substring(dashPos + 1, rangeDefinition.length()));
                    } else {
                        currentRange.end = (fileLength - 1L);
                    }
                } catch(NumberFormatException e) {
                    response.addHeader("Content-Range", "bytes */" + fileLength);

                    response.sendError(416);

                    return null;
                }
            }
            if (!currentRange.validate()) {
                response.addHeader("Content-Range", "bytes */" + fileLength);
                response.sendError(416);

                return null;
            }
            result.add(currentRange);
        }
        return result;
    }

    @Deprecated protected InputStream render(String contextPath, WebResource resource) throws IOException,
    ServletException {
        return render(contextPath, resource, null);
    }

    protected InputStream render(String contextPath, WebResource resource, String encoding) throws IOException,
    ServletException {
        Source xsltSource = findXsltSource(resource);
        if (xsltSource == null) {
            return renderHtml(contextPath, resource, encoding);
        }
        return renderXml(contextPath, resource, xsltSource, encoding);
    }

    @Deprecated protected InputStream renderXml(String contextPath, WebResource resource, Source xsltSource) throws IOException,
    ServletException {
        return renderXml(contextPath, resource, xsltSource, null);
    }

    protected InputStream renderXml(String contextPath, WebResource resource, Source xsltSource, String encoding) throws IOException,
    ServletException {
        StringBuilder sb = new StringBuilder();

        sb.append("<?xml version=\"1.0\"?>");
        sb.append("<listing ");
        sb.append(" contextPath='");
        sb.append(contextPath);
        sb.append("'");
        sb.append(" directory='");
        sb.append(resource.getName());
        sb.append("' ");
        sb.append(" hasParent='").append(!resource.getName().equals("/"));
        sb.append("'>");

        sb.append("<entries>");

        String[] entries = this.resources.list(resource.getWebappPath());

        String rewrittenContextPath = rewriteUrl(contextPath);
        String directoryWebappPath = resource.getWebappPath();
        for (String entry: entries) {
            if ((!entry.equalsIgnoreCase("WEB-INF")) && (!entry.equalsIgnoreCase("META-INF")) && (!entry.equalsIgnoreCase(this.localXsltFile))) {
                if (! (directoryWebappPath + entry).equals(this.contextXsltFile)) {
                    WebResource childResource = this.resources.getResource(directoryWebappPath + entry);
                    if (childResource.exists()) {
                        sb.append("<entry");
                        sb.append(" type='").append(childResource.isDirectory() ? "dir": "file").append("'");

                        sb.append(" urlPath='").append(rewrittenContextPath).append(rewriteUrl(directoryWebappPath + entry)).append(childResource.isDirectory() ? "/": "").append("'");
                        if (childResource.isFile()) {
                            sb.append(" size='").append(renderSize(childResource.getContentLength())).append("'");
                        }
                        sb.append(" date='").append(childResource.getLastModifiedHttp()).append("'");

                        sb.append(">");
                        sb.append(RequestUtil.filter(entry));
                        if (childResource.isDirectory()) {
                            sb.append("/");
                        }
                        sb.append("</entry>");
                    }
                }
            }
        }
        sb.append("</entries>");

        String readme = getReadme(resource, encoding);
        if (readme != null) {
            sb.append("<readme><![CDATA[");
            sb.append(readme);
            sb.append("]]></readme>");
        }
        sb.append("</listing>");
        ClassLoader original;
        if (Globals.IS_SECURITY_ENABLED) {
            PrivilegedGetTccl pa = new PrivilegedGetTccl();
            original = (ClassLoader) AccessController.doPrivileged(pa);
        } else {
            original = Thread.currentThread().getContextClassLoader();
        }
        try {
            if (Globals.IS_SECURITY_ENABLED) {
                PrivilegedSetTccl pa = new PrivilegedSetTccl(DefaultServlet.class.getClassLoader());

                AccessController.doPrivileged(pa);
            } else {
                Thread.currentThread().setContextClassLoader(DefaultServlet.class.getClassLoader());
            }
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Source xmlSource = new StreamSource(new StringReader(sb.toString()));
            Transformer transformer = tFactory.newTransformer(xsltSource);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            OutputStreamWriter osWriter = new OutputStreamWriter(stream, "UTF8");
            StreamResult out = new StreamResult(osWriter);
            transformer.transform(xmlSource, out);
            osWriter.flush();
            PrivilegedSetTccl pa;
            return new ByteArrayInputStream(stream.toByteArray());
        } catch(TransformerException e) {
            throw new ServletException("XSL transformer error", e);
        } finally {
            if (Globals.IS_SECURITY_ENABLED) {
                PrivilegedSetTccl pa = new PrivilegedSetTccl(original);
                AccessController.doPrivileged(pa);
            } else {
                Thread.currentThread().setContextClassLoader(original);
            }
        }
    }

    @Deprecated protected InputStream renderHtml(String contextPath, WebResource resource) throws IOException {
        return renderHtml(contextPath, resource, null);
    }

    protected InputStream renderHtml(String contextPath, WebResource resource, String encoding) throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        OutputStreamWriter osWriter = new OutputStreamWriter(stream, "UTF8");
        PrintWriter writer = new PrintWriter(osWriter);

        StringBuilder sb = new StringBuilder();

        String[] entries = this.resources.list(resource.getWebappPath());

        String rewrittenContextPath = rewriteUrl(contextPath);
        String directoryWebappPath = resource.getWebappPath();

        sb.append("<html>\r\n");
        sb.append("<head>\r\n");
        sb.append("<title>");
        sb.append(sm.getString("directory.title", new Object[] {
            directoryWebappPath
        }));
        sb.append("</title>\r\n");
        sb.append("<STYLE><!--");
        sb.append("h1 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:22px;} h2 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:16px;} h3 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:14px;} body {font-family:Tahoma,Arial,sans-serif;color:black;background-color:white;} b {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;} p {font-family:Tahoma,Arial,sans-serif;background:white;color:black;font-size:12px;} a {color:black;} a.name {color:black;} .line {height:1px;background-color:#525D76;border:none;}");
        sb.append("--></STYLE> ");
        sb.append("</head>\r\n");
        sb.append("<body>");
        sb.append("<h1>");
        sb.append(sm.getString("directory.title", new Object[] {
            directoryWebappPath
        }));

        String parentDirectory = directoryWebappPath;
        if (parentDirectory.endsWith("/")) {
            parentDirectory = parentDirectory.substring(0, parentDirectory.length() - 1);
        }
        int slash = parentDirectory.lastIndexOf('/');
        if (slash >= 0) {
            String parent = directoryWebappPath.substring(0, slash);
            sb.append(" - <a href=\"");
            sb.append(rewrittenContextPath);
            if (parent.equals("")) {
                parent = "/";
            }
            sb.append(rewriteUrl(parent));
            if (!parent.endsWith("/")) {
                sb.append("/");
            }
            sb.append("\">");
            sb.append("<b>");
            sb.append(sm.getString("directory.parent", new Object[] {
                parent
            }));
            sb.append("</b>");
            sb.append("</a>");
        }
        sb.append("</h1>");
        sb.append("<HR size=\"1\" noshade=\"noshade\">");

        sb.append("<table width=\"100%\" cellspacing=\"0\" cellpadding=\"5\" align=\"center\">\r\n");

        sb.append("<tr>\r\n");
        sb.append("<td align=\"left\"><font size=\"+1\"><strong>");
        sb.append(sm.getString("directory.filename"));
        sb.append("</strong></font></td>\r\n");
        sb.append("<td align=\"center\"><font size=\"+1\"><strong>");
        sb.append(sm.getString("directory.size"));
        sb.append("</strong></font></td>\r\n");
        sb.append("<td align=\"right\"><font size=\"+1\"><strong>");
        sb.append(sm.getString("directory.lastModified"));
        sb.append("</strong></font></td>\r\n");
        sb.append("</tr>");

        boolean shade = false;
        for (String entry: entries) {
            if ((!entry.equalsIgnoreCase("WEB-INF")) && (!entry.equalsIgnoreCase("META-INF"))) {
                WebResource childResource = this.resources.getResource(directoryWebappPath + entry);
                if (childResource.exists()) {
                    sb.append("<tr");
                    if (shade) {
                        sb.append(" bgcolor=\"#eeeeee\"");
                    }
                    sb.append(">\r\n");
                    shade = !shade;

                    sb.append("<td align=\"left\">&nbsp;&nbsp;\r\n");
                    sb.append("<a href=\"");
                    sb.append(rewrittenContextPath);
                    sb.append(rewriteUrl(directoryWebappPath + entry));
                    if (childResource.isDirectory()) {
                        sb.append("/");
                    }
                    sb.append("\"><tt>");
                    sb.append(RequestUtil.filter(entry));
                    if (childResource.isDirectory()) {
                        sb.append("/");
                    }
                    sb.append("</tt></a></td>\r\n");

                    sb.append("<td align=\"right\"><tt>");
                    if (childResource.isDirectory()) {
                        sb.append("&nbsp;");
                    } else {
                        sb.append(renderSize(childResource.getContentLength()));
                    }
                    sb.append("</tt></td>\r\n");

                    sb.append("<td align=\"right\"><tt>");
                    sb.append(childResource.getLastModifiedHttp());
                    sb.append("</tt></td>\r\n");

                    sb.append("</tr>\r\n");
                }
            }
        }
        sb.append("</table>\r\n");

        sb.append("<HR size=\"1\" noshade=\"noshade\">");

        String readme = getReadme(resource, encoding);
        if (readme != null) {
            sb.append(readme);
            sb.append("<HR size=\"1\" noshade=\"noshade\">");
        }
        if (this.showServerInfo) {
            sb.append("<h3>").append(ServerInfo.getServerInfo()).append("</h3>");
        }
        sb.append("</body>\r\n");
        sb.append("</html>\r\n");

        writer.write(sb.toString());
        writer.flush();
        return new ByteArrayInputStream(stream.toByteArray());
    }

    protected String renderSize(long size) {
        long leftSide = size / 1024L;
        long rightSide = size % 1024L / 103L;
        if ((leftSide == 0L) && (rightSide == 0L) && (size > 0L)) {
            rightSide = 1L;
        }
        return "" + leftSide + "." + rightSide + " kb";
    }

    @Deprecated protected String getReadme(WebResource directory) {
        return getReadme(directory, null);
    }

    protected String getReadme(WebResource directory, String encoding) {
        if (this.readmeFile != null) {
            WebResource resource = this.resources.getResource(directory.getWebappPath() + this.readmeFile);
            StringWriter buffer;
            if (resource.isFile()) {
                buffer = new StringWriter();
                InputStreamReader reader = null;
                try {
                    InputStream is = resource.getInputStream();
                    Throwable localThrowable2 = null;
                    try {
                        if (encoding != null) {
                            reader = new InputStreamReader(is, encoding);
                        } else {
                            reader = new InputStreamReader(is);
                        }
                        copyRange(reader, new PrintWriter(buffer));
                    } catch(Throwable localThrowable1) {
                        localThrowable2 = localThrowable1;
                        throw localThrowable1;
                    } finally {
                        if (is != null) {
                            if (localThrowable2 != null) {
                                try {
                                    is.close();
                                } catch(Throwable x2) {
                                    localThrowable2.addSuppressed(x2);
                                }
                            } else {
                                is.close();
                            }
                        }
                    }
                    return buffer.toString();
                } catch(IOException e) {
                    log("Failure to close reader", e);
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch(IOException localIOException3) {}
                    }
                }
            }
            if (this.debug > 10) {
                log("readme '" + this.readmeFile + "' not found");
            }
            return null;
        }
        return null;
    }

    protected Source findXsltSource(WebResource directory) throws IOException {
        if (this.localXsltFile != null) {
            WebResource resource = this.resources.getResource(directory.getWebappPath() + this.localXsltFile);
            if (resource.isFile()) {
                InputStream is = resource.getInputStream();
                if (is != null) {
                    if (Globals.IS_SECURITY_ENABLED) {
                        return secureXslt(is);
                    }
                    return new StreamSource(is);
                }
            }
            if (this.debug > 10) {
                log("localXsltFile '" + this.localXsltFile + "' not found");
            }
        }
        if (this.contextXsltFile != null) {
            InputStream is = getServletContext().getResourceAsStream(this.contextXsltFile);
            if (is != null) {
                if (Globals.IS_SECURITY_ENABLED) {
                    return secureXslt(is);
                }
                return new StreamSource(is);
            }
            if (this.debug > 10) {
                log("contextXsltFile '" + this.contextXsltFile + "' not found");
            }
        }
        if (this.globalXsltFile != null) {
            File f = validateGlobalXsltFile();
            if (f != null) {
                FileInputStream fis = new FileInputStream(f);
                Throwable localThrowable2 = null;
                try {
                    byte[] b = new byte[(int) f.length()];
                    fis.read(b);
                    return new StreamSource(new ByteArrayInputStream(b));
                } catch(Throwable localThrowable1) {
                    localThrowable2 = localThrowable1;
                    throw localThrowable1;
                } finally {
                    if (fis != null) {
                        if (localThrowable2 != null) {
                            try {
                                fis.close();
                            } catch(Throwable x2) {
                                localThrowable2.addSuppressed(x2);
                            }
                        } else {
                            fis.close();
                        }
                    }
                }
            }
        }
        return null;
    }

    private File validateGlobalXsltFile() {
        Context context = this.resources.getContext();

        File baseConf = new File(context.getCatalinaBase(), "conf");
        File result = validateGlobalXsltFile(baseConf);
        if (result == null) {
            File homeConf = new File(context.getCatalinaHome(), "conf");
            if (!baseConf.equals(homeConf)) {
                result = validateGlobalXsltFile(homeConf);
            }
        }
        return result;
    }

    private File validateGlobalXsltFile(File base) {
        File candidate = new File(this.globalXsltFile);
        if (!candidate.isAbsolute()) {
            candidate = new File(base, this.globalXsltFile);
        }
        if (!candidate.isFile()) {
            return null;
        }
        try {
            if (!candidate.getCanonicalPath().startsWith(base.getCanonicalPath())) {
                return null;
            }
        } catch(IOException ioe) {
            return null;
        }
        String nameLower = candidate.getName().toLowerCase(Locale.ENGLISH);
        if ((!nameLower.endsWith(".xslt")) && (!nameLower.endsWith(".xsl"))) {
            return null;
        }
        return candidate;
    }

    private Source secureXslt(InputStream is) {
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            builder.setEntityResolver(secureEntityResolver);
            Document document = builder.parse(is);
            return new DOMSource(document);
        } catch(ParserConfigurationException | SAXException | IOException e) {
            if (this.debug > 0) {
                log(e.getMessage(), e);
            }
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch(IOException localIOException2) {}
            }
        }
		return null;
    }

    protected boolean checkSendfile(HttpServletRequest request, HttpServletResponse response, WebResource resource, long length, Range range) {
        String canonicalPath;
        if ((this.sendfileSize > 0) && (length > this.sendfileSize) && (Boolean.TRUE.equals(request.getAttribute("org.apache.tomcat.sendfile.support"))) && (request.getClass().getName().equals("org.apache.catalina.connector.RequestFacade")) && (response.getClass().getName().equals("org.apache.catalina.connector.ResponseFacade")) && (resource.isFile()) && ((canonicalPath = resource.getCanonicalPath()) != null)) {
            request.setAttribute("org.apache.tomcat.sendfile.filename", canonicalPath);
            if (range == null) {
                request.setAttribute("org.apache.tomcat.sendfile.start", Long.valueOf(0L));
                request.setAttribute("org.apache.tomcat.sendfile.end", Long.valueOf(length));
            } else {
                request.setAttribute("org.apache.tomcat.sendfile.start", Long.valueOf(range.start));
                request.setAttribute("org.apache.tomcat.sendfile.end", Long.valueOf(range.end + 1L));
            }
            return true;
        }
        return false;
    }

    protected boolean checkIfMatch(HttpServletRequest request, HttpServletResponse response, WebResource resource) throws IOException {
        String eTag = resource.getETag();
        String headerValue = request.getHeader("If-Match");
        if ((headerValue != null) && (headerValue.indexOf('*') == -1)) {
            StringTokenizer commaTokenizer = new StringTokenizer(headerValue, ",");

            boolean conditionSatisfied = false;
            while ((!conditionSatisfied) && (commaTokenizer.hasMoreTokens())) {
                String currentToken = commaTokenizer.nextToken();
                if (currentToken.trim().equals(eTag)) {
                    conditionSatisfied = true;
                }
            }
            if (!conditionSatisfied) {
                response.sendError(412);

                return false;
            }
        }
        return true;
    }

    protected boolean checkIfModifiedSince(HttpServletRequest request, HttpServletResponse response, WebResource resource) {
        try {
            long headerValue = request.getDateHeader("If-Modified-Since");
            long lastModified = resource.getLastModified();
            if (headerValue != -1L) {
                if ((request.getHeader("If-None-Match") == null) && (lastModified < headerValue + 1000L)) {
                    response.setStatus(304);
                    response.setHeader("ETag", resource.getETag());

                    return false;
                }
            }
        } catch(IllegalArgumentException illegalArgument) {
            return true;
        }
        return true;
    }

    protected boolean checkIfNoneMatch(HttpServletRequest request, HttpServletResponse response, WebResource resource) throws IOException {
        String eTag = resource.getETag();
        String headerValue = request.getHeader("If-None-Match");
        if (headerValue != null) {
            boolean conditionSatisfied = false;
            if (!headerValue.equals("*")) {
                StringTokenizer commaTokenizer = new StringTokenizer(headerValue, ",");
                while ((!conditionSatisfied) && (commaTokenizer.hasMoreTokens())) {
                    String currentToken = commaTokenizer.nextToken();
                    if (currentToken.trim().equals(eTag)) {
                        conditionSatisfied = true;
                    }
                }
            } else {
                conditionSatisfied = true;
            }
            if (conditionSatisfied) {
                if (("GET".equals(request.getMethod())) || ("HEAD".equals(request.getMethod()))) {
                    response.setStatus(304);
                    response.setHeader("ETag", eTag);

                    return false;
                }
                response.sendError(412);
                return false;
            }
        }
        return true;
    }

    protected boolean checkIfUnmodifiedSince(HttpServletRequest request, HttpServletResponse response, WebResource resource) throws IOException {
        try {
            long lastModified = resource.getLastModified();
            long headerValue = request.getDateHeader("If-Unmodified-Since");
            if ((headerValue != -1L) && (lastModified >= headerValue + 1000L)) {
                response.sendError(412);
                return false;
            }
        } catch(IllegalArgumentException illegalArgument) {
            return true;
        }
        return true;
    }

    @Deprecated protected void copy(WebResource resource, InputStream is, ServletOutputStream ostream) throws IOException {
        copy(is, ostream);
    }

    protected void copy(InputStream is, ServletOutputStream ostream) throws IOException {
        IOException exception = null;
        InputStream istream = new BufferedInputStream(is, this.input);

        exception = copyRange(istream, ostream);

        istream.close();
        if (exception != null) {
            throw exception;
        }
    }

    @Deprecated protected void copy(WebResource resource, InputStream is, PrintWriter writer, String encoding) throws IOException {
        copy(is, writer, encoding);
    }

    protected void copy(InputStream is, PrintWriter writer, String encoding) throws IOException {
        IOException exception = null;
        Reader reader;
        if (encoding == null) {
            reader = new InputStreamReader(is);
        } else {
            reader = new InputStreamReader(is, encoding);
        }
        exception = copyRange(reader, writer);

        reader.close();
        if (exception != null) {
            throw exception;
        }
    }

    protected void copy(WebResource resource, ServletOutputStream ostream, Range range) throws IOException {
        IOException exception = null;

        InputStream resourceInputStream = resource.getInputStream();
        InputStream istream = new BufferedInputStream(resourceInputStream, this.input);

        exception = copyRange(istream, ostream, range.start, range.end);

        istream.close();
        if (exception != null) {
            throw exception;
        }
    }

    protected void copy(WebResource resource, ServletOutputStream ostream, Iterator < Range > ranges, String contentType) throws IOException {
        IOException exception = null;
        while ((exception == null) && (ranges.hasNext())) {
            InputStream resourceInputStream = resource.getInputStream();
            InputStream istream = new BufferedInputStream(resourceInputStream, this.input);
            Throwable localThrowable2 = null;
            try {
                Range currentRange = (Range) ranges.next();

                ostream.println();
                ostream.println("--CATALINA_MIME_BOUNDARY");
                if (contentType != null) {
                    ostream.println("Content-Type: " + contentType);
                }
                ostream.println("Content-Range: bytes " + currentRange.start + "-" + currentRange.end + "/" + currentRange.length);

                ostream.println();

                exception = copyRange(istream, ostream, currentRange.start, currentRange.end);
            } catch(Throwable localThrowable1) {
                localThrowable2 = localThrowable1;
                throw localThrowable1;
            } finally {
                if (istream != null) {
                    if (localThrowable2 != null) {
                        try {
                            istream.close();
                        } catch(Throwable x2) {
                            localThrowable2.addSuppressed(x2);
                        }
                    } else {
                        istream.close();
                    }
                }
            }
        }
        ostream.println();
        ostream.print("--CATALINA_MIME_BOUNDARY--");
        if (exception != null) {
            throw exception;
        }
    }

    protected IOException copyRange(InputStream istream, ServletOutputStream ostream) {
    	IOException exception = null;
        byte[] buffer = new byte[this.input];
        int len = buffer.length;
        try {
            for (;;) {
                len = istream.read(buffer);
                if (len == -1) {
                    break;
                }
                ostream.write(buffer, 0, len);
            }
        } catch(IOException e) {
            exception = e;
            len = -1;
        }
		return exception;
    }

    protected IOException copyRange(Reader reader, PrintWriter writer) {
    	IOException exception = null;
        char[] buffer = new char[this.input];
        int len = buffer.length;
        try {
            for (;;) {
                len = reader.read(buffer);
                if (len == -1) {
                    break;
                }
                writer.write(buffer, 0, len);
            }
        } catch(IOException e) {
            exception = e;
            len = -1;
        }
		return exception;
    }

    protected IOException copyRange(InputStream istream, ServletOutputStream ostream, long start, long end) {
        if (this.debug > 10) {
            log("Serving bytes:" + start + "-" + end);
        }
        long skipped = 0L;
        try {
            skipped = istream.skip(start);
        } catch(IOException e) {
            return e;
        }
        if (skipped < start) {
            return new IOException(sm.getString("defaultservlet.skipfail", new Object[] {
                Long.valueOf(skipped),
                Long.valueOf(start)
            }));
        }
        IOException exception = null;
        long bytesToRead = end - start + 1L;

        byte[] buffer = new byte[this.input];
        int len = buffer.length;
        while ((bytesToRead > 0L) && (len >= buffer.length)) {
            try {
                len = istream.read(buffer);
                if (bytesToRead >= len) {
                    ostream.write(buffer, 0, len);
                    bytesToRead -= len;
                } else {
                    ostream.write(buffer, 0, (int) bytesToRead);
                    bytesToRead = 0L;
                }
            } catch(IOException e) {
                exception = e;
                len = -1;
            }
            if (len < buffer.length) {
                break;
            }
        }
        return exception;
    }

    public void destroy() {}

    protected static class Range {
        public long start;
        public long end;
        public long length;

        public boolean validate() {
            if (this.end >= this.length) {
                this.end = (this.length - 1L);
            }
            return (this.start >= 0L) && (this.end >= 0L) && (this.start <= this.end) && (this.length > 0L);
        }
    }

    protected static class CompressionFormat implements Serializable {
        private static final long serialVersionUID = 1L;
        public final String extension;
        public final String encoding;

        public CompressionFormat(String extension, String encoding) {
            this.extension = extension;
            this.encoding = encoding;
        }
    }

    private static class PrecompressedResource {
        public final WebResource resource;
        public final DefaultServlet.CompressionFormat format;

        private PrecompressedResource(WebResource resource, DefaultServlet.CompressionFormat format) {
            this.resource = resource;
            this.format = format;
        }
    }

    private static class SecureEntityResolver implements EntityResolver2 {
        public InputSource resolveEntity(String publicId, String systemId) throws SAXException,
        IOException {
            throw new SAXException(DefaultServlet.sm.getString("defaultServlet.blockExternalEntity", new Object[] {
                publicId,
                systemId
            }));
        }

        public InputSource getExternalSubset(String name, String baseURI) throws SAXException,
        IOException {
            throw new SAXException(DefaultServlet.sm.getString("defaultServlet.blockExternalSubset", new Object[] {
                name,
                baseURI
            }));
        }

        public InputSource resolveEntity(String name, String publicId, String baseURI, String systemId) throws SAXException,
        IOException {
            throw new SAXException(DefaultServlet.sm.getString("defaultServlet.blockExternalEntity2", new Object[] {
                name,
                publicId,
                baseURI,
                systemId
            }));
        }
    }
}