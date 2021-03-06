/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.defeasiblejustification.parser;


import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import org.defeasiblejustification.model.DefeasibleImplication;
import org.defeasiblejustification.model.DefeasibleLogicSymbol;
import org.tweetyproject.logics.pl.syntax.*;

import org.tweetyproject.logics.pl.parser.PlParser;

/**
 *
 * @author stevewang
 */
public class DefeasibleParser
{
    PlParser classicalParser;
    
    public DefeasibleParser(PlParser classicalParser)
    {
        this.classicalParser = classicalParser;
    }
    
    public PlFormula parseFormula(String formula) throws Exception
    {
        Stack<Object> stack = new Stack<Object>();
        for (int i = 0; i < formula.length(); i++)
        {
            consumeToken(stack, formula.charAt(i));
        }
        return this.parseDefeasibleImplication(stack);
    }
    
    private void consumeToken(Stack<Object> stack, char c) throws Exception
    {
        String token =  "" + c;
        if (token.equals(" "))
        {
            // Do nothing
        }
        else if (token.equals(")"))
        {
            if (!stack.contains("("))
                throw new Exception("Parse Exception: unmatching brackets.");
            List<Object> subFormula = new ArrayList<Object>();
            for (Object o = stack.pop(); !((o instanceof String) && ((String) o).equals("(")); o = stack.pop())
		subFormula.add(0, o);
            PlFormula x = null;
            if (subFormula.contains(DefeasibleLogicSymbol.DEFEASIBLEIMPLICATION()))
                x = parseDefeasibleImplication(subFormula);
            else
                x = classicalParser.parseFormula(constructString(subFormula));
            stack.push(x);
        }
        else if (token.equals(">") && stack.lastElement().equals("~"))
        {
            stack.pop();
            stack.push(DefeasibleLogicSymbol.DEFEASIBLEIMPLICATION());
        }
        else
        {
            stack.push(token);
        }
    }
    
    private PlFormula parseDefeasibleImplication(List<Object> formula) throws Exception
    {
        if (formula.isEmpty())
            throw new Exception("Empty parentheses.");
        if (!hasDefeasibleImplication(formula))
            return classicalParser.parseFormula(constructString(formula));
        if (formula.size() == 1 && formula.get(0) instanceof DefeasibleImplication)
            return (DefeasibleImplication)formula.get(0);
        
        
        List<Object> left = new ArrayList<Object>();
        List<Object> right = new ArrayList<Object>();
        boolean isRightFormula = false;
        for (Object o : formula)
        {
            if ((o instanceof String) && ((String)o).equals(DefeasibleLogicSymbol.DEFEASIBLEIMPLICATION()))
                isRightFormula = true;
            else if (isRightFormula)
                right.add(o);
            else 
                left.add(o);
        }
        
        return new DefeasibleImplication(parseDefeasibleImplication(left), parseDefeasibleImplication(right));
        
    }
    
    private String constructString(List<Object> list)
    {
        StringBuilder stringBuilder = new StringBuilder();
        for (Object o : list)
        {
            stringBuilder.append(o.toString());
        }
        return stringBuilder.toString();
    }
    
    private boolean hasDefeasibleImplication(List<Object> list)
    {
        if (list.contains(DefeasibleLogicSymbol.DEFEASIBLEIMPLICATION()))
            return true;
        for (Object o : list)
        {
            if (o instanceof DefeasibleImplication)
                return true;
        }
        return false;
    }
    
    private void printSubFormula(List<Object> list)
    {
        for (Object o : list)
        {
            System.out.println(o.toString());
        }
    }
    
}
