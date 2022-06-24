/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.defeasiblejustification.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.defeasiblejustification.model.DefeasibleImplication;
import org.tweetyproject.logics.pl.syntax.Implication;
import org.tweetyproject.logics.pl.syntax.PlBeliefSet;
import org.tweetyproject.logics.pl.syntax.PlFormula;
import org.tweetyproject.logics.pl.syntax.Proposition;

/**
 *
 * @author stevewang
 */
public class Utils 
{
    public static List<PlFormula> union(List<PlFormula> list1, List<PlFormula> list2)
    {
        if (list1 == null || list1.isEmpty())
            return list2;
        
        if (list2 == null || list2.isEmpty())
            return list1;
        
        Set<PlFormula> set = new HashSet<PlFormula>();
        set.addAll(list1);
        set.addAll(list2);
        return new ArrayList<PlFormula>(set);
    }
    
    public static List<PlFormula> materialise(List<PlFormula> formulaList) throws Exception
    {
        List<PlFormula> materialised = new ArrayList<PlFormula>();
        for (PlFormula formula : formulaList)
        {
            materialised.add(materialise(formula));
        }
        return materialised;
    }
    
    public static PlFormula materialise(PlFormula formula)
    {
        if (formula instanceof DefeasibleImplication)
        {
            Implication implication = new Implication(((DefeasibleImplication) formula).getFormula());
            return implication;
        }
        else
        {
            return formula;
        }
    }
    
    public static PlBeliefSet remove(PlBeliefSet knowledgeBase, List<PlFormula> formulas)
    {
        knowledgeBase.removeAll(formulas);
        return knowledgeBase;
    }
    
    public static void print(List<PlFormula> list)
    {
        if (list == null || list.isEmpty())
            System.out.println("null");
        for(PlFormula plFormula: list)
            System.out.println(plFormula);
    }
    
    public static void printPropositions(List<Proposition> list)
    {
        if (list == null || list.isEmpty())
            System.out.println("null");
        for(Proposition proposition: list)
            System.out.println(proposition);
    }
}
