package com.tsystems.javaschool.tasks.calculator;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {

    /**
     * Evaluate statement represented as string.
     *
     * @param statement mathematical statement containing digits, '.' (dot) as decimal mark,
     *                  parentheses, operations signs '+', '-', '*', '/'<br>
     *                  Example: <code>(1 + 38) * 4.5 - 1 / 2.</code>
     * @return string value containing result of evaluation or null if statement is invalid
     */
    public String evaluate(String statement) {
        //Checking for correct values
        if (statement == null || statement.contains(",") || statement.equals("")
                || statement.contains("..") || statement.contains("**") || statement.contains("//")
                || statement.contains("++") || statement.contains("--"))
            return null;

        //Counting round brackets
        int openP = 0;
        int closeP = 0;
        Pattern pattern = Pattern.compile("\\(");
        Matcher matcher = pattern.matcher(statement);
        while (matcher.find()) {
            ++openP;
        }
        pattern = Pattern.compile("\\)");
        matcher = pattern.matcher(statement);
        while (matcher.find()) {
            ++closeP;
        }

        if (openP != closeP)
            return null;

        //Finding every expression in brackets and evaluate() it
        pattern = Pattern.compile("\\((.+)\\)");
        matcher = pattern.matcher(statement);
        while (matcher.find()) {
            String subQuery = matcher.group(1);

            statement = statement.replace("(" + subQuery + ")", evaluate(subQuery));

        }


        List<Double> nums = new ArrayList<>();
        pattern = Pattern.compile("-?[0-9]+\\.?[0-9]*");
        matcher = pattern.matcher(statement);
        while (matcher.find()) {
            nums.add(Double.parseDouble(matcher.group()));

        }

        //Replacing minuses between nums with pluses
        pattern = Pattern.compile("[0-9]+-[0-9]+");
        matcher = pattern.matcher(statement);
        while (matcher.find()) {
            statement = statement.replaceFirst(matcher.group(),
                    matcher.group().replaceFirst("-", "+"));
        }


        List<String> opers = new ArrayList<>();
        pattern = Pattern.compile("[\\/\\*\\+]");
        matcher = pattern.matcher(statement);
        while (matcher.find()) {
            opers.add(matcher.group());

        }

        //Calculating * and /
        try {
            for (int i = 0; i < opers.size(); i++) {
                if (opers.get(i).equals("/")) {
                    if (nums.get(i + 1) == 0.0){
                        return null;}
                    nums.set(i + 1, nums.get(i) / nums.get(i + 1));
                    nums.set(i, 0.0);
                } else if (opers.get(i).equals("*")) {
                    nums.set(i + 1, nums.get(i) * nums.get(i + 1));
                    nums.set(i, 0.0);
                }
            }
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
        // Calculating +
        Double result = 0.0;
        for (Double d : nums) {

            result += d;
        }
        //If result is decimal fraction then formatting it to its type
        if (result % 1 == 0){
            return String.format("%.0f", result);
        }

        String strResult = String.format("%.4f", result);
        strResult = strResult.replace(',', '.').replaceFirst("0+$", "");
        return strResult;
    }

}
