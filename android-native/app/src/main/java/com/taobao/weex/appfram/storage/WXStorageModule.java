package com.taobao.weex.appfram.storage;

import android.text.TextUtils;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.appfram.storage.IWXStorageAdapter;
import com.taobao.weex.bridge.JSCallback;
import java.util.Map;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class WXStorageModule extends WXSDKEngine.DestroyableModule implements IWXStorage {
    IWXStorageAdapter mStorageAdapter;

    private IWXStorageAdapter ability() {
        IWXStorageAdapter iWXStorageAdapter = this.mStorageAdapter;
        if (iWXStorageAdapter != null) {
            return iWXStorageAdapter;
        }
        IWXStorageAdapter iWXStorageAdapter2 = WXSDKEngine.getIWXStorageAdapter();
        this.mStorageAdapter = iWXStorageAdapter2;
        return iWXStorageAdapter2;
    }

    @Override // com.taobao.weex.common.Destroyable
    public void destroy() {
        IWXStorageAdapter iWXStorageAdapterAbility = ability();
        if (iWXStorageAdapterAbility != null) {
            iWXStorageAdapterAbility.close();
        }
    }

    @Override // com.taobao.weex.appfram.storage.IWXStorage
    @JSMethod(uiThread = false)
    public void getAllKeys(final JSCallback jSCallback) {
        IWXStorageAdapter iWXStorageAdapterAbility = ability();
        if (iWXStorageAdapterAbility == null) {
            StorageResultHandler.handleNoHandlerError(jSCallback);
        } else {
            iWXStorageAdapterAbility.getAllKeys(new IWXStorageAdapter.OnResultReceivedListener() { // from class: com.taobao.weex.appfram.storage.WXStorageModule.5
                @Override // com.taobao.weex.appfram.storage.IWXStorageAdapter.OnResultReceivedListener
                public void onReceived(Map<String, Object> map) {
                    JSCallback jSCallback2 = jSCallback;
                    if (jSCallback2 != null) {
                        jSCallback2.invoke(map);
                    }
                }
            });
        }
    }

    @Override // com.taobao.weex.appfram.storage.IWXStorage
    @JSMethod(uiThread = false)
    public void getItem(String str, final JSCallback jSCallback) {
        if (TextUtils.isEmpty(str)) {
            StorageResultHandler.handleInvalidParam(jSCallback);
            return;
        }
        IWXStorageAdapter iWXStorageAdapterAbility = ability();
        if (iWXStorageAdapterAbility == null) {
            StorageResultHandler.handleNoHandlerError(jSCallback);
        } else {
            iWXStorageAdapterAbility.getItem(str, new IWXStorageAdapter.OnResultReceivedListener() { // from class: com.taobao.weex.appfram.storage.WXStorageModule.2
                @Override // com.taobao.weex.appfram.storage.IWXStorageAdapter.OnResultReceivedListener
                public void onReceived(Map<String, Object> map) {
                    JSCallback jSCallback2 = jSCallback;
                    if (jSCallback2 != null) {
                        jSCallback2.invoke(map);
                    }
                }
            });
        }
    }

    @Override // com.taobao.weex.appfram.storage.IWXStorage
    @JSMethod(uiThread = false)
    public void length(final JSCallback jSCallback) {
        IWXStorageAdapter iWXStorageAdapterAbility = ability();
        if (iWXStorageAdapterAbility == null) {
            StorageResultHandler.handleNoHandlerError(jSCallback);
        } else {
            iWXStorageAdapterAbility.length(new IWXStorageAdapter.OnResultReceivedListener() { // from class: com.taobao.weex.appfram.storage.WXStorageModule.4
                @Override // com.taobao.weex.appfram.storage.IWXStorageAdapter.OnResultReceivedListener
                public void onReceived(Map<String, Object> map) {
                    JSCallback jSCallback2 = jSCallback;
                    if (jSCallback2 != null) {
                        jSCallback2.invoke(map);
                    }
                }
            });
        }
    }

    @Override // com.taobao.weex.appfram.storage.IWXStorage
    @JSMethod(uiThread = false)
    public void removeItem(String str, final JSCallback jSCallback) {
        if (TextUtils.isEmpty(str)) {
            StorageResultHandler.handleInvalidParam(jSCallback);
            return;
        }
        IWXStorageAdapter iWXStorageAdapterAbility = ability();
        if (iWXStorageAdapterAbility == null) {
            StorageResultHandler.handleNoHandlerError(jSCallback);
        } else {
            iWXStorageAdapterAbility.removeItem(str, new IWXStorageAdapter.OnResultReceivedListener() { // from class: com.taobao.weex.appfram.storage.WXStorageModule.3
                @Override // com.taobao.weex.appfram.storage.IWXStorageAdapter.OnResultReceivedListener
                public void onReceived(Map<String, Object> map) {
                    JSCallback jSCallback2 = jSCallback;
                    if (jSCallback2 != null) {
                        jSCallback2.invoke(map);
                    }
                }
            });
        }
    }

    @Override // com.taobao.weex.appfram.storage.IWXStorage
    @JSMethod(uiThread = false)
    public void setItem(String str, String str2, final JSCallback jSCallback) {
        if (TextUtils.isEmpty(str) || str2 == null) {
            StorageResultHandler.handleInvalidParam(jSCallback);
            return;
        }
        IWXStorageAdapter iWXStorageAdapterAbility = ability();
        if (iWXStorageAdapterAbility == null) {
            StorageResultHandler.handleNoHandlerError(jSCallback);
        } else {
            iWXStorageAdapterAbility.setItem(str, str2, new IWXStorageAdapter.OnResultReceivedListener() { // from class: com.taobao.weex.appfram.storage.WXStorageModule.1
                @Override // com.taobao.weex.appfram.storage.IWXStorageAdapter.OnResultReceivedListener
                public void onReceived(Map<String, Object> map) {
                    JSCallback jSCallback2 = jSCallback;
                    if (jSCallback2 != null) {
                        jSCallback2.invoke(map);
                    }
                }
            });
        }
    }

    @Override // com.taobao.weex.appfram.storage.IWXStorage
    @JSMethod(uiThread = false)
    public void setItemPersistent(String str, String str2, final JSCallback jSCallback) {
        if (TextUtils.isEmpty(str) || str2 == null) {
            StorageResultHandler.handleInvalidParam(jSCallback);
            return;
        }
        IWXStorageAdapter iWXStorageAdapterAbility = ability();
        if (iWXStorageAdapterAbility == null) {
            StorageResultHandler.handleNoHandlerError(jSCallback);
        } else {
            iWXStorageAdapterAbility.setItemPersistent(str, str2, new IWXStorageAdapter.OnResultReceivedListener() { // from class: com.taobao.weex.appfram.storage.WXStorageModule.6
                @Override // com.taobao.weex.appfram.storage.IWXStorageAdapter.OnResultReceivedListener
                public void onReceived(Map<String, Object> map) {
                    JSCallback jSCallback2 = jSCallback;
                    if (jSCallback2 != null) {
                        jSCallback2.invoke(map);
                    }
                }
            });
        }
    }
}
