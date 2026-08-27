package io.dcloud.common.util.net.http;

import io.dcloud.common.DHInterface.AbsMgr;
import io.dcloud.common.DHInterface.IMgr;
import io.dcloud.common.adapter.util.DeviceInfo;
import io.dcloud.common.adapter.util.Logger;
import io.dcloud.common.adapter.util.PlatformUtil;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.common.util.IOUtil;
import io.dcloud.common.util.PdrUtil;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class Response implements Runnable {
    AbsMgr mNetMgr;
    Socket mSocket;
    private static final byte CR = 13;
    private static final byte LF = 10;
    private static final byte[] CRLF = {CR, LF};
    String mUrl = null;
    final int BUFFER_SIZE = 10240;

    public Response(Socket socket, AbsMgr absMgr) {
        this.mSocket = socket;
        this.mNetMgr = absMgr;
        new Thread(this).start();
    }

    private void addResponseHead(long j, OutputStream outputStream) throws IOException {
        writeHeader(outputStream, "HTTP/1.1 200 OK");
        writeHeader(outputStream, "Content-Type: " + PdrUtil.getMimeType(this.mUrl));
        writeHeader(outputStream, "Access-Control-Allow-Origin: *");
        writeHeader(outputStream, "Access-Control-Allow-Headers: *");
        writeHeader(outputStream, "Content-Length: " + j);
        outputStream.write(CRLF);
        outputStream.flush();
    }

    private void write(OutputStream outputStream, String str) throws IOException {
        outputStream.write(str.getBytes());
    }

    private void writeRequest(OutputStream outputStream, String str) throws IOException {
        outputStream.write("GET /index.html HTTP/1.1".getBytes());
        byte[] bArr = CRLF;
        outputStream.write(bArr);
        outputStream.write(("Host: " + str).getBytes());
        outputStream.write(bArr);
        outputStream.write(bArr);
        outputStream.flush();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.lang.Runnable
    public void run() throws Throwable {
        InputStream inputStream;
        InputStream inputStream2;
        OutputStream outputStream;
        InputStream resInputStream;
        InputStream inputStream3;
        OutputStream outputStream2 = null;
        OutputStream outputStream3 = null;
        InputStream inputStream4 = null;
        outputStream2 = null;
        outputStream2 = null;
        outputStream2 = null;
        try {
            inputStream2 = this.mSocket.getInputStream();
            try {
                Request request = new Request(inputStream2);
                request.parse();
                String data = request.getData();
                if (data.startsWith(AbsoluteConst.SOCKET_NATIVE_COMMAND)) {
                    this.mNetMgr.processEvent(IMgr.MgrType.AppMgr, 7, data);
                    inputStream3 = null;
                } else {
                    try {
                        if (data.startsWith(AbsoluteConst.SOCKET_CONNECTION)) {
                            String strSubstring = data.substring(6);
                            outputStream = this.mSocket.getOutputStream();
                            String str = PdrUtil.isEquals(strSubstring, AbsoluteConst.SOCKET_CONN_REQUEST_ROOT_PATH) ? DeviceInfo.sDeviceRootDir : "";
                            Logger.d("miniserver", strSubstring, str);
                            outputStream.write(str.getBytes());
                        } else {
                            String uri = request.getUri();
                            this.mUrl = uri;
                            if (uri == null) {
                                try {
                                    IOUtil.close(inputStream2);
                                    IOUtil.close((InputStream) null);
                                    IOUtil.close((OutputStream) null);
                                    this.mSocket.close();
                                    return;
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    return;
                                }
                            }
                            outputStream = this.mSocket.getOutputStream();
                            byte[] bArr = new byte[10240];
                            if (this.mUrl.startsWith(AbsoluteConst.MINI_SERVER_BASE_RES)) {
                                resInputStream = PlatformUtil.getResInputStream("res/" + this.mUrl.substring(5));
                            } else {
                                resInputStream = (InputStream) this.mNetMgr.processEvent(IMgr.MgrType.AppMgr, 2, this.mUrl);
                            }
                            inputStream4 = resInputStream;
                            if (inputStream4 != null) {
                                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                while (true) {
                                    int i = inputStream4.read(bArr);
                                    if (i <= 0) {
                                        break;
                                    } else {
                                        byteArrayOutputStream.write(bArr, 0, i);
                                    }
                                }
                                byte[] byteArray = byteArrayOutputStream.toByteArray();
                                addResponseHead(byteArray.length, outputStream);
                                outputStream.write(byteArray);
                            } else {
                                Logger.i("miniserver", "error url=" + this.mUrl);
                                outputStream.write("HTTP/1.1 404 File Not Found\r\nContent-Type: text/html\r\nContent-Length: 23\r\n\r\n<h1>File Not Found</h1>".getBytes());
                            }
                        }
                        inputStream3 = inputStream4;
                        outputStream3 = outputStream;
                    } catch (IOException e2) {
                        e = e2;
                        inputStream = null;
                        outputStream2 = request;
                        try {
                            e.printStackTrace();
                            try {
                                IOUtil.close(inputStream2);
                                IOUtil.close(inputStream);
                                IOUtil.close(outputStream2);
                                this.mSocket.close();
                                return;
                            } catch (Exception e3) {
                                e3.printStackTrace();
                                return;
                            }
                        } catch (Throwable th) {
                            th = th;
                            try {
                                IOUtil.close(inputStream2);
                                IOUtil.close(inputStream);
                                IOUtil.close(outputStream2);
                                this.mSocket.close();
                            } catch (Exception e4) {
                                e4.printStackTrace();
                            }
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        inputStream = null;
                        outputStream2 = request;
                        IOUtil.close(inputStream2);
                        IOUtil.close(inputStream);
                        IOUtil.close(outputStream2);
                        this.mSocket.close();
                        throw th;
                    }
                }
                try {
                    IOUtil.close(inputStream2);
                    IOUtil.close(inputStream3);
                    IOUtil.close(outputStream3);
                    this.mSocket.close();
                } catch (Exception e5) {
                    e5.printStackTrace();
                }
            } catch (IOException e6) {
                e = e6;
                inputStream = null;
            } catch (Throwable th3) {
                th = th3;
                inputStream = null;
            }
        } catch (IOException e7) {
            e = e7;
            inputStream = null;
            inputStream2 = null;
        } catch (Throwable th4) {
            th = th4;
            inputStream = null;
            inputStream2 = null;
        }
    }

    void writeHeader(OutputStream outputStream, String str) throws IOException {
        write(outputStream, str);
        outputStream.write(CRLF);
    }
}
