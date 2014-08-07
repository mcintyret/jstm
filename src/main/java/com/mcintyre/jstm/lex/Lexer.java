package com.mcintyre.jstm.lex;

import com.mcintyre.jstm.lex.token.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Character.isWhitespace;

/**
 * User: tommcintyre
 * Date: 8/6/14
 */
public class Lexer {

    private static final String OPERATOR_SYMBOLS = "!?<>=-+%^&|*:;/[]{}().";

    private static final String STANDALONE_OPERATORS = "?:;[]{}()";

    private static final String NUMBER_SYMBOLS = "1234567890.";

    private enum State {
        NONE,
        IDENTIFIER_OR_KEYWORD,
        STRING_LITERAL,
        NUMBER_LITERAL,
        OPERATOR
    }


    public List<Token> lex(Reader input) throws IOException {
        input = new ExtraWhitespaceReader(input);
        List<Token> tokens = new ArrayList<>();
        try {
            State state = State.NONE;

            int bufIndex = 0;
            // TODO: deal with huge string literals where this breaks
            char[] buf = new char[1024];

            int read;
            while ((read = input.read()) >= 0) {
                char c = (char) read;

                // Now it gets hairy!
                switch (state) {
                    case NONE:
                        if (!isWhitespace(c)) {
                            bufIndex = 0;
                            if (c == '"') {
                                state = State.STRING_LITERAL;
                            } else {
                                buf[bufIndex++] = c;
                                if (NUMBER_SYMBOLS.indexOf(c) >= 0) {
                                    state = State.NUMBER_LITERAL;
                                } else if (isOperator(c)) {
                                    state = State.OPERATOR;
                                } else {
                                    state = State.IDENTIFIER_OR_KEYWORD;
                                }
                            }
                        }
                        break;
                    case IDENTIFIER_OR_KEYWORD:
                        if (isWhitespace(c)) {
                            state = State.NONE;
                            tokens.add(getIdentifierOrKeywordToken(new String(buf, 0, bufIndex)));
                        } else {
                            if (isOperator(c)) {
                                state = State.OPERATOR;
                                tokens.add(getIdentifierOrKeywordToken(new String(buf, 0, bufIndex)));
                                bufIndex = 0;
                            }
                            buf[bufIndex++] = c;
                        }
                        break;
                    case STRING_LITERAL:
                        // TODO: escaping
                        if (c == '"') {
                            state = State.NONE;
                            tokens.add(new StringLiteral(new String(buf, 0, bufIndex)));
                        } else {
                            buf[bufIndex++] = c;
                        }
                        break;
                    case NUMBER_LITERAL:
                        if (isWhitespace(c)) {
                            state = State.NONE;
                            tokens.add(getNumberLiteralToken(new String(buf, 0, bufIndex)));
                        } else {
                            if (isOperator(c)) {
                                state = State.OPERATOR;
                                tokens.add(getNumberLiteralToken(new String(buf, 0, bufIndex)));
                                bufIndex = 0;
                            }
                            buf[bufIndex++] = c;
                        }
                        break;
                    case OPERATOR:
                        if (!isOperator(c)) {
                            tokens.addAll(getOperatorTokens(new String(buf, 0, bufIndex)));
                            if (isWhitespace(c)) {
                                state = State.NONE;
                            } else {
                                bufIndex = 0;
                                if (c == '"') {
                                    state = State.STRING_LITERAL;
                                } else {
                                    buf[bufIndex++] = c;
                                    if (NUMBER_SYMBOLS.indexOf(c) >= 0) {
                                        state = State.NUMBER_LITERAL;
                                    } else {
                                        state = State.IDENTIFIER_OR_KEYWORD;
                                    }
                                }
                            }
                        } else {
                            buf[bufIndex++] = c;
                        }
                        break;
                    default:
                        throw new IllegalStateException();
                }
            }
            return tokens;
        } catch (Throwable t) {
            System.out.println(tokens);
            throw t;
        }
    }

    private static boolean isOperator(char c) {
        return OPERATOR_SYMBOLS.indexOf(c) >= 0;
    }

    private static Token getIdentifierOrKeywordToken(String val) {
        Keyword keyword = Keyword.forString(val);
        if (keyword != null) {
            return keyword;
        } else {
            if (!isValidIdenfifier(val)) {
                throw new IllegalArgumentException("Invalid identifier: '" + val + "'");
            }
            return new Identifier(val);
        }
    }

    private static Token getNumberLiteralToken(String val) {
        try {
            return new NumberLiteral(Double.parseDouble(val));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Illegal numeric value: '" + val + "'");
        }
    }

    private static List<Token> getOperatorTokens(String val) {
        List<Token> operators = new ArrayList<>(Math.min(val.length(), 4));

        int start = 0;
        for (int i = 0; i < val.length(); i++) {
            char c = val.charAt(i);

            if (STANDALONE_OPERATORS.indexOf(c) >= 0) {
                if (i > start) {
                    operators.add(parseOperator(val.substring(start, i)));
                }
                operators.add(parseOperator(new String(new char[]{c})));
                start = i + 1;
            }
        }

        if (start < val.length()) {
            operators.add(parseOperator(val.substring(start)));
        }

        return operators;
    }

    private static Operator parseOperator(String val) {
        Operator operator = Operator.forString(val);
        if (operator == null) {
            throw new IllegalArgumentException("Illegal operator: '" + val + "'");
        }
        return operator;
    }


    // TODO
    private static boolean isValidIdenfifier(String val) {
        return true;
    }

    public static void main(String[] args) throws IOException {
        System.out.println(new Lexer().lex(new BufferedReader(new InputStreamReader(Lexer.class.getResourceAsStream("/test.js")))));
    }


    private static class ExtraWhitespaceReader extends Reader {

        private final Reader delegate;

        private boolean finished = false;

        private ExtraWhitespaceReader(Reader delegate) {
            this.delegate = delegate;
        }

        @Override
        public int read() throws IOException {
            int r = delegate.read();
            if (r < 0 && !finished) {
                finished = true;
                return (int) ' ';
            }
            return r;
        }

        @Override
        public int read(char[] cbuf, int off, int len) throws IOException {
            return delegate.read(cbuf, off, len);
        }

        @Override
        public void close() throws IOException {
            delegate.close();
        }
    }

}
