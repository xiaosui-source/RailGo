package com.taobao.weex.el.parse;

import com.alibaba.fastjson.JSONArray;
import java.util.List;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
class Block extends Token {
    private List<Token> tokens;

    public Block(List<Token> list, int i) {
        super("", i);
        this.tokens = list;
    }

    @Override // com.taobao.weex.el.parse.Token
    public Object execute(Object obj) {
        if (getType() != 7) {
            List<Token> list = this.tokens;
            if (list == null || list.size() == 0) {
                return null;
            }
            return this.tokens.get(0).execute(obj);
        }
        List<Token> list2 = this.tokens;
        if (list2 == null || list2.size() == 0) {
            return new JSONArray(4);
        }
        JSONArray jSONArray = new JSONArray(this.tokens.size());
        for (int i = 0; i < this.tokens.size(); i++) {
            Token token = this.tokens.get(i);
            if (token == null) {
                jSONArray.add(null);
            } else {
                jSONArray.add(token.execute(obj));
            }
        }
        return jSONArray;
    }

    @Override // com.taobao.weex.el.parse.Token
    public String toString() {
        if (getType() == 7) {
            return "" + this.tokens + "";
        }
        List<Token> list = this.tokens;
        if (list == null || list.size() != 1) {
            return Operators.BLOCK_START_STR + this.tokens + Operators.BLOCK_END;
        }
        return Operators.BLOCK_START_STR + this.tokens.get(0) + Operators.BLOCK_END;
    }
}
