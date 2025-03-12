package com.tksimeji.kunectron.markupextension.token;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

final class TokenizerImpl implements Tokenizer {
    private static class TokenizeInfo {
        public final @NotNull String input;

        public int position = 0;

        public TokenizeInfo(final @NotNull String input) {
            this.input = input;
        }
    }

    private @NotNull TokenFactory factory;

    public TokenizerImpl() {
        this(TokenFactory.create());
    }

    public TokenizerImpl(final @NotNull TokenFactory factory) {
        setFactory(factory);
    }

    @Override
    public @NotNull TokenFactory getFactory() {
        return factory;
    }

    @Override
    public void setFactory(final @NotNull TokenFactory factory) {
        this.factory = factory;
    }

    @Override
    public @NotNull List<Token> tokenize(final @NotNull String input) {
        TokenizeInfo info = new TokenizeInfo(input);

        List<Token> tokens = new ArrayList<>();

        while (info.position < input.length()) {
            char c = input.charAt(info.position);

            if (Character.isWhitespace(c)) {
                info.position++;
            } else if (c == '+' || c == '-' || c == '*' || c == '/') {
                tokens.add(factory.createBinaryOperator(String.valueOf(c)));
                info.position++;
            } else if (c == '=' && info.position + 1 < info.input.length() && info.input.charAt(info.position + 1) == '=') {
                if (info.position + 2 < info.input.length() && info.input.charAt(info.position + 2) == '=') {
                    tokens.add(factory.createBinaryOperator("==="));
                    info.position += 3;
                } else {
                    tokens.add(factory.createBinaryOperator("=="));
                    info.position += 2;
                }
            } else if (c == '!' && info.position + 1 < info.input.length() && info.input.charAt(info.position + 1) == '=') {
                if (info.position + 2 < info.input.length() && info.input.charAt(info.position + 2) == '=') {
                    tokens.add(factory.createBinaryOperator("!=="));
                    info.position += 3;
                } else {
                    tokens.add(factory.createBinaryOperator("!="));
                    info.position += 2;
                }
            } else if (c == '&' && info.position + 1 < info.input.length() && info.input.charAt(info.position + 1) == '&') {
                tokens.add(factory.createBinaryOperator("&&"));
                info.position += 2;
            } else if (c == '|' && info.position + 1 < info.input.length() && info.input.charAt(info.position + 1) == '|') {
                tokens.add(factory.createBinaryOperator("||"));
                info.position += 2;
            } else if (c == '!') {
                tokens.add(factory.createUnaryOperator("!"));
                info.position++;
            } else if (c == '(') {
                tokens.add(factory.createLeftParen());
                info.position++;
            } else if (c == ')') {
                tokens.add(factory.createRightParen());
                info.position++;
            } else if (c == '.') {
                tokens.add(factory.createDot());
                info.position++;
            } else if (c == ',') {
                tokens.add(factory.createComma());
                info.position++;
            } else if (Character.isDigit(c)) {
                tokens.add(factory.createNumber(parseNumber(info)));
            } else if (c == '\'') {
                tokens.add(factory.createString(parseString(info)));
            } else if (Character.isLetter(c) || c == '_') {
                String identifier = parseIdentifier(info);
                if (identifier.equals("true") || identifier.equals("false")) {
                    tokens.add(factory.createBoolean(identifier));
                } else if (identifier.equals("null")) {
                    throw new IllegalArgumentException("'null' is an invalid keyword.");
                } else {
                    tokens.add(factory.createIdentifier(identifier));
                }
            } else {
                throw new IllegalArgumentException("Invalid character: " + c);
            }
        }

        return tokens;
    }

    private @NotNull String parseNumber(final @NotNull TokenizeInfo info) {
        StringBuilder builder = new StringBuilder();
        while (info.position < info.input.length() && (Character.isDigit(info.input.charAt(info.position)) || info.input.charAt(info.position) == '.')) {
            builder.append(info.input.charAt(info.position++));
        }
        return builder.toString();
    }

    private @NotNull String parseString(final @NotNull TokenizeInfo info) {
        StringBuilder builder = new StringBuilder();
        info.position++;
        while (info.position < info.input.length() && info.input.charAt(info.position) != '\'') {
            builder.append(info.input.charAt(info.position++));
        }
        info.position++;
        return builder.toString();
    }

    private @NotNull String parseIdentifier(final @NotNull TokenizeInfo info) {
        StringBuilder builder = new StringBuilder();
        while (info.position < info.input.length() && (Character.isLetterOrDigit(info.input.charAt(info.position)) || info.input.charAt(info.position) == '_')) {
            builder.append(info.input.charAt(info.position++));
        }
        return builder.toString();
    }
}
