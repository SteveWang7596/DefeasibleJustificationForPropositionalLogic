/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.defeasiblejustification.classicalreasoner;

import org.tweetyproject.logics.pl.reasoner.SatReasoner;
import org.tweetyproject.logics.pl.sat.Sat4jSolver;
import org.tweetyproject.logics.pl.sat.SatSolver;
import org.tweetyproject.logics.pl.syntax.Implication;
import org.tweetyproject.logics.pl.syntax.Negation;
import org.tweetyproject.logics.pl.syntax.PlBeliefSet;
import org.tweetyproject.logics.pl.syntax.PlFormula;
import org.tweetyproject.logics.pl.syntax.Proposition;

/**
 *
 * @author stevewang
 */
public class ClassicalReasonerTester 
{
    public static void main(String[] args) 
    {
        PlBeliefSet knowledgeBase = new PlBeliefSet();
        PlFormula formula = new Implication(new Proposition("p"), new Proposition("b"));
        knowledgeBase.add(formula);
        formula = new Implication(new Proposition("r"), new Proposition("b"));
        knowledgeBase.add(formula);
        formula = new Implication(new Proposition("f"), new Proposition("m"));
        knowledgeBase.add(formula);
        formula = new Implication(new Proposition("p"), new Negation(new Proposition("f")));
        knowledgeBase.add(formula);
        
        PlFormula query = new Implication(new Proposition("p"), new Negation(new Proposition("m")));
        
        SatSolver.setDefaultSolver(new Sat4jSolver());
        SatReasoner reasoner = new SatReasoner();
        
        System.out.println("Result = " + reasoner.query(knowledgeBase, query));
    }
}
