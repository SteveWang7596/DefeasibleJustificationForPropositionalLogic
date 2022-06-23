/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.defeasiblejustification.classicaljustifiaction;

import org.defeasiblejustification.classicalJustification.ClassicalJustification;
import org.tweetyproject.logics.pl.syntax.Implication;
import org.tweetyproject.logics.pl.syntax.PlBeliefSet;
import org.tweetyproject.logics.pl.syntax.PlFormula;
import org.tweetyproject.logics.pl.syntax.Proposition;

/**
 *
 * @author SteveW
 */
public class ClassicalJustificationTester 
{
    public static void main(String[] args) 
    {
        PlBeliefSet knowledgeBase = new PlBeliefSet();
        PlFormula formula = new Implication( new Proposition("a"), new Proposition("b"));
        knowledgeBase.add(formula);
        formula = new Implication( new Proposition("b"), new Proposition("c"));
        knowledgeBase.add(formula);
        //formula = new Implication( new Proposition("a"), new Proposition("c"));
        //knowledgeBase.add(formula);
        formula = new Implication( new Proposition("b"), new Proposition("d"));
        knowledgeBase.add(formula);
        formula = new Implication( new Proposition("d"), new Proposition("c"));
        knowledgeBase.add(formula);
        formula = new Implication( new Proposition("a"), new Proposition("d"));
        knowledgeBase.add(formula);
        
        PlFormula query = new Implication(new Proposition("a"), new Proposition("c"));
        
        ClassicalJustification.computeJustification(knowledgeBase, query);
    }
}
