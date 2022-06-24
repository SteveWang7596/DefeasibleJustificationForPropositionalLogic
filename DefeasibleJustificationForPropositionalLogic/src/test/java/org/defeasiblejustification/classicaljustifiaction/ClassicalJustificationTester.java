/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.defeasiblejustification.classicaljustifiaction;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.defeasiblejustification.classicalJustification.ClassicalJustification;
import org.tweetyproject.commons.ParserException;
import org.tweetyproject.logics.pl.parser.PlParser;
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
        try 
        {
            PlBeliefSet knowledgeBase = new PlBeliefSet();
            PlParser classicalParser = new PlParser();
            
            String inputKnowledgeBasePath = "src/test/java/org/defeasiblejustification/classicaljustifiaction/classicalJustificationTestExample1.txt";
            Scanner scanner = new Scanner(new File(inputKnowledgeBasePath));
            while (scanner.hasNextLine())
            {
                knowledgeBase.add(classicalParser.parseFormula(scanner.nextLine()));
            }
            
            PlFormula query = new Implication(new Proposition("a"), new Proposition("c"));
            System.out.println("Query = " + query );
            
            ClassicalJustification.computeJustification(knowledgeBase, query);
            
            
        } 
        catch (FileNotFoundException ex) 
        {
            Logger.getLogger(ClassicalJustificationTester.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ClassicalJustificationTester.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserException ex) {
            Logger.getLogger(ClassicalJustificationTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
