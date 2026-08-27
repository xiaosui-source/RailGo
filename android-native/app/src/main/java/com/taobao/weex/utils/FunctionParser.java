package com.taobao.weex.utils;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class FunctionParser<K, V> {
    public static final char SPACE = ' ';
    private Lexer lexer;
    private Mapper<K, V> mapper;

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    private static class Lexer {
        private static final char A_LOWER = 'a';
        private static final char A_UPPER = 'A';
        private static final String COMMA = ",";
        private static final char DOT = '.';
        private static final String LEFT_PARENT = "(";
        private static final char MINUS = '-';
        private static final char NINE = '9';
        private static final char PLUS = '+';
        private static final String RIGHT_PARENT = ")";
        private static final char ZERO = '0';
        private static final char Z_LOWER = 'z';
        private static final char Z_UPPER = 'Z';
        private Token current;
        private int pointer;
        private String source;
        private String value;

        /* JADX INFO: Access modifiers changed from: private */
        public Token getCurrentToken() {
            return this.current;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public String getCurrentTokenValue() {
            return this.value;
        }

        private boolean isCharacterOrDigit(char c) {
            if ('0' <= c && c <= '9') {
                return true;
            }
            if ('a' > c || c > 'z') {
                return 'A' <= c && c <= 'Z';
            }
            return true;
        }

        private boolean isFuncName(CharSequence charSequence) {
            for (int i = 0; i < charSequence.length(); i++) {
                char cCharAt = charSequence.charAt(i);
                if (('a' > cCharAt || cCharAt > 'z') && (('A' > cCharAt || cCharAt > 'Z') && cCharAt != '-')) {
                    return false;
                }
            }
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean moveOn() {
            int i = this.pointer;
            while (true) {
                if (this.pointer >= this.source.length()) {
                    break;
                }
                char cCharAt = this.source.charAt(this.pointer);
                if (cCharAt == ' ') {
                    int i2 = this.pointer;
                    this.pointer = i2 + 1;
                    if (i != i2) {
                        break;
                    }
                    i++;
                } else if (isCharacterOrDigit(cCharAt) || cCharAt == '.' || cCharAt == '%' || cCharAt == '-' || cCharAt == '+') {
                    this.pointer++;
                } else {
                    int i3 = this.pointer;
                    if (i == i3) {
                        this.pointer = i3 + 1;
                    }
                }
            }
            int i4 = this.pointer;
            if (i != i4) {
                moveOn(this.source.substring(i, i4));
                return true;
            }
            this.current = null;
            this.value = null;
            return false;
        }

        private Lexer(String str) {
            this.pointer = 0;
            this.source = str;
        }

        private void moveOn(String str) {
            if ("(".equals(str)) {
                this.current = Token.LEFT_PARENT;
                this.value = "(";
                return;
            }
            if (")".equals(str)) {
                this.current = Token.RIGHT_PARENT;
                this.value = ")";
            } else if (",".equals(str)) {
                this.current = Token.COMMA;
                this.value = ",";
            } else if (isFuncName(str)) {
                this.current = Token.FUNC_NAME;
                this.value = str;
            } else {
                this.current = Token.PARAM_VALUE;
                this.value = str;
            }
        }
    }

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    public interface Mapper<K, V> {
        Map<K, V> map(String str, List<String> list);
    }

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    private enum Token {
        FUNC_NAME,
        PARAM_VALUE,
        LEFT_PARENT,
        RIGHT_PARENT,
        COMMA
    }

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    private static class WXInterpretationException extends RuntimeException {
        private WXInterpretationException(String str) {
            super(str);
        }
    }

    public FunctionParser(String str, Mapper<K, V> mapper) {
        this.lexer = new Lexer(str);
        this.mapper = mapper;
    }

    private LinkedHashMap<K, V> definition() {
        LinkedHashMap<K, V> linkedHashMap = new LinkedHashMap<>();
        do {
            linkedHashMap.putAll(function());
        } while (this.lexer.getCurrentToken() == Token.FUNC_NAME);
        return linkedHashMap;
    }

    private Map<K, V> function() {
        LinkedList linkedList = new LinkedList();
        String strMatch = match(Token.FUNC_NAME);
        match(Token.LEFT_PARENT);
        linkedList.add(match(Token.PARAM_VALUE));
        while (true) {
            Token currentToken = this.lexer.getCurrentToken();
            Token token = Token.COMMA;
            if (currentToken != token) {
                match(Token.RIGHT_PARENT);
                return this.mapper.map(strMatch, linkedList);
            }
            match(token);
            linkedList.add(match(Token.PARAM_VALUE));
        }
    }

    private String match(Token token) {
        try {
            if (token != this.lexer.getCurrentToken()) {
                return "";
            }
            String currentTokenValue = this.lexer.getCurrentTokenValue();
            this.lexer.moveOn();
            return currentTokenValue;
        } catch (Exception unused) {
            WXLogUtils.e(token + "Token doesn't match" + this.lexer.source);
            return "";
        }
    }

    public LinkedHashMap<K, V> parse() {
        this.lexer.moveOn();
        return definition();
    }
}
