package com.alibaba.android.bindingx.core.internal;

import com.alibaba.android.bindingx.core.LogProxy;
import com.taobao.weex.ui.component.richtext.node.RichTextNode;
import io.dcloud.common.constant.AbsoluteConst;
import java.util.ArrayList;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/* loaded from: classes.dex */
class Expression {
    private JSONObject root;

    Expression(String str) {
        try {
            this.root = (JSONObject) new JSONTokener(str).nextValue();
        } catch (Throwable th) {
            LogProxy.e("[Expression] expression is illegal. \n ", th);
        }
    }

    Expression(JSONObject jSONObject) {
        this.root = jSONObject;
    }

    Object execute(Map<String, Object> map) throws JSONException, IllegalArgumentException {
        return execute(this.root, map);
    }

    private double toNumber(Object obj) {
        if (obj instanceof String) {
            return Double.parseDouble((String) obj);
        }
        if (obj instanceof Boolean) {
            return ((Boolean) obj).booleanValue() ? 1.0d : 0.0d;
        }
        return ((Double) obj).doubleValue();
    }

    private boolean toBoolean(Object obj) {
        if (obj instanceof String) {
            return "".equals(obj);
        }
        if (obj instanceof Double) {
            return ((Double) obj).doubleValue() != 0.0d;
        }
        return ((Boolean) obj).booleanValue();
    }

    private String toString(Object obj) {
        if (obj instanceof Boolean) {
            return ((Boolean) obj).booleanValue() ? AbsoluteConst.TRUE : AbsoluteConst.FALSE;
        }
        if (obj instanceof Double) {
            return Double.toString(((Double) obj).doubleValue());
        }
        return (String) obj;
    }

    private boolean equal(Object obj, Object obj2) {
        if ((obj instanceof JSObjectInterface) && (obj2 instanceof JSObjectInterface)) {
            return obj == obj2;
        }
        if ((obj instanceof String) && (obj2 instanceof String)) {
            return obj.equals(obj2);
        }
        return ((obj instanceof Boolean) && (obj2 instanceof Boolean)) ? toBoolean(obj) == toBoolean(obj2) : toNumber(obj) == toNumber(obj2);
    }

    private boolean strictlyEqual(Object obj, Object obj2) {
        if ((obj instanceof JSObjectInterface) && !(obj2 instanceof JSObjectInterface)) {
            return false;
        }
        if ((obj instanceof Boolean) && !(obj2 instanceof Boolean)) {
            return false;
        }
        if (!(obj instanceof Double) || (obj2 instanceof Double)) {
            return (!(obj instanceof String) || (obj2 instanceof String)) && obj == obj2;
        }
        return false;
    }

    private Object execute(JSONObject jSONObject, Map<String, Object> map) throws JSONException, IllegalArgumentException {
        JSONArray jSONArrayOptJSONArray;
        int i;
        String string = jSONObject.getString("type");
        jSONArrayOptJSONArray = jSONObject.optJSONArray(RichTextNode.CHILDREN);
        string.hashCode();
        switch (string) {
            case "CallExpression":
                JSFunctionInterface jSFunctionInterface = (JSFunctionInterface) execute(jSONArrayOptJSONArray.getJSONObject(0), map);
                ArrayList<Object> arrayList = new ArrayList<>();
                JSONArray jSONArray = jSONArrayOptJSONArray.getJSONObject(1).getJSONArray(RichTextNode.CHILDREN);
                for (i = 0; i < jSONArray.length(); i++) {
                    arrayList.add(execute(jSONArray.getJSONObject(i), map));
                }
                return jSFunctionInterface.execute(arrayList);
            case "!":
                return Boolean.valueOf(!toBoolean(execute(jSONArrayOptJSONArray.getJSONObject(0), map)));
            case "%":
                return Double.valueOf(toNumber(execute(jSONArrayOptJSONArray.getJSONObject(0), map)) % toNumber(execute(jSONArrayOptJSONArray.getJSONObject(1), map)));
            case "*":
                return Double.valueOf(toNumber(execute(jSONArrayOptJSONArray.getJSONObject(0), map)) * toNumber(execute(jSONArrayOptJSONArray.getJSONObject(1), map)));
            case "+":
                return Double.valueOf(toNumber(execute(jSONArrayOptJSONArray.getJSONObject(0), map)) + toNumber(execute(jSONArrayOptJSONArray.getJSONObject(1), map)));
            case "-":
                return Double.valueOf(toNumber(execute(jSONArrayOptJSONArray.getJSONObject(0), map)) - toNumber(execute(jSONArrayOptJSONArray.getJSONObject(1), map)));
            case "/":
                return Double.valueOf(toNumber(execute(jSONArrayOptJSONArray.getJSONObject(0), map)) / toNumber(execute(jSONArrayOptJSONArray.getJSONObject(1), map)));
            case "<":
                return Boolean.valueOf(toNumber(execute(jSONArrayOptJSONArray.getJSONObject(0), map)) < toNumber(execute(jSONArrayOptJSONArray.getJSONObject(1), map)));
            case ">":
                return Boolean.valueOf(toNumber(execute(jSONArrayOptJSONArray.getJSONObject(0), map)) > toNumber(execute(jSONArrayOptJSONArray.getJSONObject(1), map)));
            case "?":
                if (((Boolean) execute(jSONArrayOptJSONArray.getJSONObject(0), map)).booleanValue()) {
                    return execute(jSONArrayOptJSONArray.getJSONObject(1), map);
                }
                return execute(jSONArrayOptJSONArray.getJSONObject(2), map);
            case "!=":
                return Boolean.valueOf(!equal(execute(jSONArrayOptJSONArray.getJSONObject(0), map), execute(jSONArrayOptJSONArray.getJSONObject(1), map)));
            case "&&":
                Object objExecute = execute(jSONArrayOptJSONArray.getJSONObject(0), map);
                return !toBoolean(objExecute) ? objExecute : execute(jSONArrayOptJSONArray.getJSONObject(1), map);
            case "**":
                return Double.valueOf(Math.pow(toNumber(execute(jSONArrayOptJSONArray.getJSONObject(0), map)), toNumber(execute(jSONArrayOptJSONArray.getJSONObject(1), map))));
            case "<=":
                return Boolean.valueOf(toNumber(execute(jSONArrayOptJSONArray.getJSONObject(0), map)) <= toNumber(execute(jSONArrayOptJSONArray.getJSONObject(1), map)));
            case "==":
                return Boolean.valueOf(equal(execute(jSONArrayOptJSONArray.getJSONObject(0), map), execute(jSONArrayOptJSONArray.getJSONObject(1), map)));
            case ">=":
                return Boolean.valueOf(toNumber(execute(jSONArrayOptJSONArray.getJSONObject(0), map)) >= toNumber(execute(jSONArrayOptJSONArray.getJSONObject(1), map)));
            case "||":
                Object objExecute2 = execute(jSONArrayOptJSONArray.getJSONObject(0), map);
                return toBoolean(objExecute2) ? objExecute2 : execute(jSONArrayOptJSONArray.getJSONObject(1), map);
            case "!==":
                return Boolean.valueOf(!strictlyEqual(execute(jSONArrayOptJSONArray.getJSONObject(0), map), execute(jSONArrayOptJSONArray.getJSONObject(1), map)));
            case "===":
                return Boolean.valueOf(strictlyEqual(execute(jSONArrayOptJSONArray.getJSONObject(0), map), execute(jSONArrayOptJSONArray.getJSONObject(1), map)));
            case "NumericLiteral":
                return Double.valueOf(jSONObject.getDouble("value"));
            case "Identifier":
                return map.get(jSONObject.getString("value"));
            case "StringLiteral":
                return jSONObject.getString("value");
            case "BooleanLiteral":
                return Boolean.valueOf(jSONObject.getBoolean("value"));
            default:
                return null;
        }
    }
}
