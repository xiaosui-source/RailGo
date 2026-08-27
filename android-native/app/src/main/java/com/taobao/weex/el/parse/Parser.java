package com.taobao.weex.el.parse;

import com.taobao.weex.WXEnvironment;
import com.taobao.weex.utils.WXLogUtils;
import java.util.ArrayList;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class Parser {
    private String code;
    private int position = 0;
    private ArrayStack<Token> stacks = new ArrayStack<>();
    private ArrayStack<Symbol> operators = new ArrayStack<>();

    public Parser(String str) {
        this.code = str;
    }

    private final void doStackOperators(int i) {
        while (this.operators.size() > i) {
            doOperator(this.operators.pop());
        }
    }

    void doOperator(Symbol symbol) {
        String str = symbol.op;
        if (Operators.BRACKET_START_STR.equals(str) || Operators.BLOCK_START_STR.equals(symbol.op) || Operators.ARRAY_START_STR.equals(symbol.op) || Operators.DOLLAR_STR.equals(symbol.op) || Operators.BLOCK_START_STR.equals(symbol.op)) {
            return;
        }
        int i = symbol.pos;
        int iMax = Math.max(i - 1, 0);
        if (!this.operators.isEmpty()) {
            iMax = Math.max(iMax, this.operators.peek().pos);
        }
        Operator operator = new Operator(str, 5);
        if (Operators.AND_NOT.equals(str)) {
            if (this.stacks.size() > i) {
                operator.self = this.stacks.remove(i);
                this.stacks.add(i, operator);
                return;
            }
            return;
        }
        if (this.stacks.size() > i) {
            operator.second = this.stacks.remove(i);
            if (this.stacks.size() > iMax) {
                operator.first = this.stacks.remove(iMax);
            } else if (operator.second == null) {
                return;
            }
            this.stacks.add(iMax, operator);
        }
    }

    final boolean hasNext() {
        return this.position < this.code.length();
    }

    final boolean hasNextToken() {
        while (hasNext()) {
            if (this.code.charAt(this.position) != ' ') {
                return true;
            }
            this.position++;
        }
        return false;
    }

    final char nextToken() {
        char cCharAt = this.code.charAt(this.position);
        while (cCharAt == ' ') {
            this.position++;
            int length = this.code.length();
            int i = this.position;
            if (length <= i) {
                break;
            }
            cCharAt = this.code.charAt(i);
        }
        return cCharAt;
    }

    public final Token parse() {
        while (hasNextToken()) {
            scanNextToken();
        }
        while (!this.operators.isEmpty()) {
            doOperator(this.operators.pop());
        }
        return this.stacks.size() == 1 ? this.stacks.pop() : new Block(this.stacks.getList(), 6);
    }

    final void scanArray() {
        int size = this.stacks.size();
        int size2 = this.operators.size();
        int i = this.position - 1;
        int i2 = (i < 0 || !Character.isJavaIdentifierPart(this.code.charAt(i))) ? 7 : 0;
        this.operators.push(new Symbol(Operators.ARRAY_START_STR, this.stacks.size()));
        this.position++;
        while (hasNextToken() && scanNextToken() != ']') {
        }
        if (this.stacks.size() <= size) {
            while (this.operators.size() > size2) {
                this.operators.pop();
            }
            return;
        }
        while (this.operators.size() > size2) {
            Symbol symbolPop = this.operators.pop();
            if (this.stacks.size() > size) {
                doOperator(symbolPop);
            }
        }
        ArrayList arrayList = new ArrayList(4);
        for (int i3 = size; i3 < this.stacks.size(); i3++) {
            arrayList.add(this.stacks.get(i3));
        }
        while (this.stacks.size() > size) {
            this.stacks.pop();
        }
        if (i2 == 7 || this.stacks.size() == 0) {
            this.stacks.push(new Block(arrayList, 7));
            return;
        }
        Token tokenPop = this.stacks.pop();
        Token block = arrayList.size() == 1 ? (Token) arrayList.get(0) : new Block(arrayList, 6);
        Operator operator = new Operator(Operators.DOT_STR, i2);
        operator.first = tokenPop;
        operator.second = block;
        this.stacks.push(operator);
    }

    void scanBracket() {
        int size = this.stacks.size();
        int size2 = this.operators.size();
        if (this.code.charAt(this.position) == '{') {
            this.operators.push(new Symbol(Operators.BLOCK_START_STR, this.stacks.size()));
            this.position++;
            while (hasNextToken() && scanNextToken() != '}') {
            }
        } else {
            this.operators.push(new Symbol(Operators.BRACKET_START_STR, this.stacks.size()));
            this.position++;
            while (hasNextToken() && scanNextToken() != ')') {
            }
        }
        if (this.stacks.size() <= size) {
            while (this.operators.size() > size2) {
                this.operators.pop();
            }
            return;
        }
        while (this.operators.size() > size2) {
            Symbol symbolPop = this.operators.pop();
            if (this.stacks.size() > size) {
                doOperator(symbolPop);
            }
        }
        ArrayList arrayList = new ArrayList(4);
        for (int i = size; i < this.stacks.size(); i++) {
            arrayList.add(this.stacks.get(i));
        }
        while (this.stacks.size() > size) {
            this.stacks.pop();
        }
        if (arrayList.size() == 1) {
            this.stacks.push((Token) arrayList.get(0));
        } else {
            this.stacks.push(new Block(arrayList, 6));
        }
    }

    final void scanIdentifier() {
        int i = this.position;
        this.position = i + 1;
        while (hasNext() && Character.isJavaIdentifierPart(this.code.charAt(this.position))) {
            this.position++;
        }
        String strSubstring = this.code.substring(i, this.position);
        if (strSubstring.startsWith(Operators.DOLLAR_STR)) {
            if (strSubstring.length() == 1) {
                return;
            } else {
                strSubstring = strSubstring.substring(1);
            }
        }
        this.stacks.push(new Token(strSubstring, (!Operators.KEYWORDS.containsKey(strSubstring) || (!this.operators.isEmpty() && Operators.isDot(this.operators.peek().op))) ? 0 : 4));
    }

    void scanIf() {
        Operator operator = new Operator(Operators.CONDITION_IF_STRING, 5);
        doStackOperators(0);
        if (this.stacks.size() > (this.operators.size() > 0 ? Math.max(this.operators.peek().pos, 0) : 0)) {
            operator.self = this.stacks.pop();
        }
        int size = this.stacks.size();
        int size2 = this.operators.size();
        this.position++;
        while (hasNextToken() && scanNextToken() != ':') {
        }
        while (this.operators.size() > size2) {
            doOperator(this.operators.pop());
        }
        while (this.stacks.size() > size) {
            operator.first = this.stacks.pop();
        }
        int size3 = this.operators.size();
        while (hasNextToken()) {
            scanNextToken();
            if (hasNextToken()) {
                scanNextToken();
            }
            if (this.operators.size() <= size3) {
                break;
            }
        }
        doStackOperators(size3);
        while (this.stacks.size() > size) {
            operator.second = this.stacks.pop();
        }
        this.stacks.push(operator);
    }

    final char scanNextToken() {
        char cNextToken = nextToken();
        if (cNextToken == '$') {
            this.position++;
            return cNextToken;
        }
        if (Character.isJavaIdentifierStart(cNextToken)) {
            scanIdentifier();
            return cNextToken;
        }
        if (cNextToken == '(' || cNextToken == '{') {
            scanBracket();
            return cNextToken;
        }
        if (cNextToken == '[') {
            scanArray();
            return cNextToken;
        }
        if (cNextToken == '\"' || cNextToken == '\'') {
            scanString();
            return cNextToken;
        }
        if ((cNextToken == '.' && Character.isDigit(this.code.charAt(this.position + 1))) || Character.isDigit(cNextToken)) {
            scanNumber();
            return cNextToken;
        }
        if (cNextToken == '?') {
            scanIf();
            return cNextToken;
        }
        if (cNextToken == ':' || cNextToken == ')' || cNextToken == '}' || cNextToken == ' ' || cNextToken == ']') {
            this.position++;
            return cNextToken;
        }
        scanOperator();
        return cNextToken;
    }

    final void scanNumber() {
        int i = this.position;
        boolean z = (this.code.charAt(i) == 'e' || this.code.charAt(this.position) == '.') ? false : true;
        this.position++;
        while (hasNext()) {
            char cCharAt = this.code.charAt(this.position);
            if (!Character.isDigit(cCharAt) && cCharAt != '.' && cCharAt != 'e') {
                break;
            }
            if (cCharAt == 'e' || cCharAt == '.') {
                z = false;
            }
            this.position++;
        }
        String strSubstring = this.code.substring(i, this.position);
        if (Operators.DOT_STR.equals(strSubstring)) {
            return;
        }
        this.stacks.push(z ? new Token(strSubstring, 1) : new Token(strSubstring, 2));
    }

    void scanOperator() {
        int i = this.position;
        String strSubstring = this.code.substring(this.position, Math.min(i + 3, this.code.length()));
        if (strSubstring.length() >= 3 && !Operators.OPERATORS_PRIORITY.containsKey(strSubstring)) {
            strSubstring = strSubstring.substring(0, 2);
        }
        if (strSubstring.length() >= 2 && !Operators.OPERATORS_PRIORITY.containsKey(strSubstring)) {
            strSubstring = strSubstring.substring(0, 1);
        }
        if (!Operators.OPERATORS_PRIORITY.containsKey(strSubstring)) {
            WXLogUtils.e("weex", new IllegalArgumentException(this.code.substring(0, Math.min(i + 1, this.code.length())) + " illegal code operator" + strSubstring));
            this.position = this.position + strSubstring.length();
            return;
        }
        if (!this.operators.isEmpty() && this.operators.peek() != null) {
            if (Operators.OPERATORS_PRIORITY.get(this.operators.peek().op).intValue() >= Operators.OPERATORS_PRIORITY.get(strSubstring).intValue()) {
                doOperator(this.operators.pop());
            }
        }
        if (!Operators.isOpEnd(strSubstring)) {
            this.operators.push(new Symbol(strSubstring, this.stacks.size()));
        }
        this.position += strSubstring.length();
    }

    final void scanString() {
        int i = this.position;
        ArrayStack arrayStack = new ArrayStack();
        char cCharAt = this.code.charAt(i);
        arrayStack.push(Character.valueOf(cCharAt));
        StringBuilder sb = new StringBuilder();
        this.position = i + 1;
        while (true) {
            if (this.position >= this.code.length()) {
                break;
            }
            char cCharAt2 = this.code.charAt(this.position);
            if (cCharAt2 != cCharAt) {
                sb.append(cCharAt2);
            } else if (this.code.charAt(this.position - 1) != '\\') {
                arrayStack.pop();
                if (arrayStack.size() == 0) {
                    this.position++;
                    break;
                }
            } else {
                sb.deleteCharAt(sb.length() - 1);
                sb.append(cCharAt2);
            }
            this.position++;
        }
        this.stacks.push(new Token(sb.toString(), 3));
    }

    public static Token parse(String str) {
        try {
            return new Parser(str).parse();
        } catch (Exception e) {
            if (WXEnvironment.isApkDebugable()) {
                WXLogUtils.e("code " + str, e);
            }
            return new Block(null, 6);
        }
    }
}
