package com.tksimeji.kunectron.markupextension;

import com.tksimeji.kunectron.markupextension.ast.*;
import com.tksimeji.kunectron.markupextension.operator.*;
import com.tksimeji.kunectron.markupextension.token.Token;
import com.tksimeji.kunectron.markupextension.token.Tokenizer;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

final class MarkupExtensionParserImpl implements MarkupExtensionParser {
    static class ParseInfo {
        public final @NotNull List<Token> tokens;

        public int position = 0;

        public ParseInfo(final @NotNull Collection<Token> tokens) {
            this.tokens = new ArrayList<>(tokens);
        }
    }

    private final @NotNull Map<String, BinaryOperator<?>> binaryOperators;
    private final @NotNull Map<String, UnaryOperator<?>> unaryOperators;

    public MarkupExtensionParserImpl() {
        this(List.of(new PlusOperator(), new MinusOperator(), new TimesOperator(), new DivididedByOperator(), new EqualityOperator(), new InequalityOperator(), new StrictEqualityOperator(), new StrictInequalityOperator(), new AndOperator(), new OrOperator(), new NotOperator()));
    }

    public MarkupExtensionParserImpl(final @NotNull Collection<Operator<?>> operators) {
        this.binaryOperators = operators.stream()
                .filter(operator -> operator instanceof BinaryOperator<?>)
                .collect(Collectors.toMap(Operator::getOperator, operator -> (BinaryOperator<?>) operator));
        this.unaryOperators = operators.stream()
                .filter(operator -> operator instanceof UnaryOperator<?>)
                .collect(Collectors.toMap(Operator::getOperator, operator -> (UnaryOperator<?>) operator));
    }

    @Override
    public @NotNull AstNode<?> parse(final @NotNull String input) {
        return parse(input, Tokenizer.create());
    }

    @Override
    public @NotNull AstNode<?> parse(@NotNull String input, @NotNull Tokenizer tokenizer) {
        return parse(tokenizer.tokenize(input));
    }

    public @NotNull AstNode<?> parse(final @NotNull Collection<Token> tokens) {
        ParseInfo info = new ParseInfo(tokens);
        return parseOr(info);
    }

    private @NotNull AstNode<?> parseOr(final @NotNull ParseInfo info) {
        AstNode<?> leftNode = parseAnd(info);
        while (info.position < info.tokens.size() && info.tokens.get(info.position).isBinaryOperator() && info.tokens.get(info.position).getValue().equals("||")) {
            String operator = info.tokens.get(info.position++).getValue();
            AstNode<?> rightNode = parseAnd(info);
            leftNode = new BinaryOpNode(leftNode, binaryOperators.get(operator), rightNode);
        }
        return leftNode;
    }

    private @NotNull AstNode<?> parseAnd(final @NotNull ParseInfo info) {
        AstNode<?> leftNode = parseEquality(info);
        while (info.position < info.tokens.size() && info.tokens.get(info.position).isBinaryOperator() && info.tokens.get(info.position).getValue().equals("&&")) {
            String operator = info.tokens.get(info.position++).getValue();
            AstNode<?> rightNode = parseExpression(info);
            leftNode = new BinaryOpNode(leftNode, binaryOperators.get(operator), rightNode);
        }
        return leftNode;
    }

    private @NotNull AstNode<?> parseEquality(final @NotNull ParseInfo info) {
        AstNode<?> leftNode = parseExpression(info);
        while (info.position < info.tokens.size() && info.tokens.get(info.position).isBinaryOperator() && (info.tokens.get(info.position).getValue().equals("==") || info.tokens.get(info.position).getValue().equals("!=") || info.tokens.get(info.position).getValue().equals("===") || info.tokens.get(info.position).getValue().equals("!=="))) {
            String operator = info.tokens.get(info.position++).getValue();
            AstNode<?> rightNode = parseExpression(info);
            leftNode = new BinaryOpNode(leftNode, binaryOperators.get(operator), rightNode);
        }
        return leftNode;
    }

    private @NotNull AstNode<?> parseExpression(final @NotNull ParseInfo info) {
        AstNode<?> leftNode = parseTerm(info);
        while (info.position < info.tokens.size() && info.tokens.get(info.position).isBinaryOperator() && (info.tokens.get(info.position).getValue().equals("+") || info.tokens.get(info.position).getValue().equals("-"))) {
            String operator = info.tokens.get(info.position++).getValue();
            AstNode<?> rightNode = parseTerm(info);
            leftNode = new BinaryOpNode(leftNode, binaryOperators.get(operator), rightNode);
        }
        return leftNode;
    }

    private @NotNull AstNode<?> parseTerm(final @NotNull ParseInfo info) {
        AstNode<?> leftNode = parseFactor(info);
        while (info.position < info.tokens.size() && info.tokens.get(info.position).isBinaryOperator() && (info.tokens.get(info.position).getValue().equals("*") || info.tokens.get(info.position).getValue().equals("/"))) {
            String operator = info.tokens.get(info.position++).getValue();
            AstNode<?> rightNode = parseFactor(info);
            leftNode = new BinaryOpNode(leftNode, binaryOperators.get(operator), rightNode);
        }
        return leftNode;
    }

    private @NotNull AstNode<?> parseFactor(final @NotNull ParseInfo info) {
        Token token = info.tokens.get(info.position);
        String tokenValue = token.getValue();

        if (token.isUnaryOperator()) {
            info.position++;
            AstNode<?> operand = parseFactor(info);
            return new UnaryOpNode(unaryOperators.get("!"), operand);
        }

        info.position++;

        if (token.isBoolean()) {
            return new BooleanNode(tokenValue);
        }

        if (token.isNumber() && tokenValue.contains(".")) {
            return new DoubleNumberNode(tokenValue);
        }

        if (token.isNumber()) {
            return new IntegerNumberNode(tokenValue);
        }

        if (token.isString()) {
            return new StringNode(tokenValue);
        }

        if (token.isIdentifier() && info.position < info.tokens.size() && info.tokens.get(info.position).isLeftParen()) {
            info.position++;
            List<AstNode<?>> args = new ArrayList<>();

            if (!info.tokens.get(info.position).isRightParen()) {
                args.add(parseOr(info));
                while (info.position < info.tokens.size() && info.tokens.get(info.position).isComma()) {
                    info.position++;
                    args.add(parseOr(info));
                }
            }

            info.position++;
            AstNode<?> methodCallNode = new MethodCallNode(token.getValue(), args);

            if (info.position < info.tokens.size() && info.tokens.get(info.position).isDot()) {
                info.position++;
                String member = info.tokens.get(info.position++).getValue();
                return new MemberAccessNode(methodCallNode, member);
            }

            return methodCallNode;
        }

        if (token.isIdentifier()) {
            AstNode<?> identifierNode = new IdentifierNode(token.getValue());

            if (info.position < info.tokens.size() && info.tokens.get(info.position).isDot()) {
                info.position++;
                String member = info.tokens.get(info.position++).getValue();
                return new MemberAccessNode(identifierNode, member);
            }

            return identifierNode;
        }

        if (token.isLeftParen()) {
            AstNode<?> orNode = parseOr(info);
            info.position++;
            return orNode;
        }

        throw new RuntimeException("Unexpected token: " + token.getValue());
    }
}
