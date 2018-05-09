package freshman;

import java.util.Random;

//import jssf.math.TCMatrix;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class TJGG {

	private TVector fvector;
	//private TIndividual[] findividuals;
	private TPopulation fpopulation;
	//private Random frandom;
	private TRandom frandom;
	private int fselect[];
	//public static double EMAX = Double.MAX_VALUE;


	
	
	/**
	 * コンストラクタ
	 */
	public TJGG(){
		TRandom frandom = new TRandom();
		fpopulation = new TPopulation();
		fselect = new int[0];
		//findividual = new TIndividual();
	}
	
	public TJGG(int popSize) {
		fpopulation = new TPopulation(popSize);
		fselect = new int[popSize];
	}
	
	
	@Override
	public String toString() {
		String str = fpopulation.getPopulationSize() + "\n";
		for(int i=0; i < fpopulation.getPopulationSize(); ++i) {
			str += fpopulation.getfIndividuals()[i].toString() + "\n";
		}
		return str;
	}
	
	public TJGG setPopulationSize(int popSize) {
		if(fpopulation.getPopulationSize() != popSize) {
			fpopulation = new TPopulation(popSize);
		}
		return this;
	}
	
	//set,get関係
	public void setPopulation(TPopulation pop) {
		fpopulation = pop;
	}
	
	public TPopulation getPopulation() {
		return fpopulation;
	}
	
	public  int[] getSelectint() {
		return fselect;
	}
	
	public TRandom getRandom() {
		return frandom;
	}
	
	public void setRandom(TRandom ran) {
		frandom = ran;
	}
	
	
	/**
	 * 集団の初期化  全然キレイじゃない・・・
	 * @param min
	 * @param max
	 * @param popSize
	 * @param dimension
	 * @return
	 */
	public TJGG TInitializePop(double min, double max, int popSize, int dimension) {

		if(fpopulation.getPopulationSize() != popSize) {
			//fpopulation = new TPopulation(popSize);
			fpopulation.setPopulationSize(popSize);
			//findividuals = new TIndividual[popSize];
		}
		
		TVector temv1 = new TVector();

		temv1.setDimension(dimension * popSize);

		temv1 = (frandom.TCreatRandomRange(min, max, dimension * popSize).fvector);
		
		
		for(int i=0; i < popSize; ++i) {
			TIndividual tempind = new TIndividual();
			TVector temv2 = new TVector();
			temv2.setDimension(dimension);

			for(int j=0; j< dimension; ++j) {
				temv2.setElement(j,temv1.getElement(j+5*i)) ;
			}
			tempind.setVector(temv2);
			fpopulation.setIndividual(i, tempind);

			
		}
		return this;
	}
	
	/**
	 * ランダムに親の選択
	 * @param np
	 * @return
	 */
	public TJGG TSelectParents(int np, int popSize, TPopulation fpop) {
		if(fpopulation.getPopulationSize() != np) {
			fpopulation.setPopulationSize(np);
			//fselect = new int[popSize];
		}
		
		fselect = frandom.TCreatRandomInt(np, popSize);
		TIndividual tempind = new TIndividual();
		for(int i=0; i < np; ++i) {
			fpopulation.setIndividual(i,fpop.getIndividual(fselect[i]));
			//選択した親をi番目の個体と交代
			tempind =  fpop.getIndividual(fselect[i]);
			fpop.setIndividual(fselect[i],fpop.getIndividual(i));
			fpop.setIndividual(i,tempind);
			//選択した親の評価値を無限大に
			//fpop.getIndividual(fselect[i]).setEvaluate( EMAX);
		}
		return this;
		
	}
	
	/**
	 * 親のいた場所に子を追加
	 * @param np
	 * @param popSize
	 * @param fpop
	 * @return
	 */
	public TJGG TAddChildern(int np, int popSize, TPopulation fpop) {
		if(fpopulation.getPopulationSize() != popSize) {
			fpopulation.setPopulationSize(popSize);
		}
		for(int i=0; i< np; ++i) {
			//i番目に親がいたのでそこに挿入
			fpopulation.setIndividual(i, fpop.getIndividual(i));
		}
			
		
		return this;
	}
	
	
	/**
	 * 上位の子を選択
	 * @param np
	 * @param fpop
	 * @return
	 */
	public TJGG TSelectEVal(int np, TPopulation fpop) {
		if(fpopulation.getPopulationSize() != np) {
			fpopulation.setPopulationSize(np);
		}
		fpop.sortByEvaluatel();
		for(int i=0; i < np; ++i) {
			fpopulation.setIndividual(i,fpop.getIndividual(i));
		}
		return this;
	}
	
	/**
	 * k-tabletで評価
	 */
	public TJGG Tktablet(int dimension){
		for(int i=0; i< fpopulation.getPopulationSize(); ++i) {
			fvector = fpopulation.getIndividual(i).getVector();
			double feval = 0.0;
			int k = (int) dimension /4;
			for(int j=0; j < dimension; ++j) {
				double xj = fvector.getElement(j);
				if(i < k) {
					feval += xj * xj;
				}else {
					feval += 10000.0 * xj * xj;
				}
			}
			fpopulation.getIndividual(i).setEvaluate(feval);
		}
		return this;
	}
	
	
	
	public static void main(String[] args) {

		TJGGTest();
	}
	
	public static void TJGGTest() {
		
		double min= -5.0;
		double max= 5.0;
		int dimension = 5;//20; 
		int popSize= 10;//14*dimension;
		int np = 2;

		

		TJGG jgg1 = new TJGG();
		TJGG jgg2 = new TJGG();
		TJGG jgg3 = new TJGG();

		jgg1.getRandom();
		jgg1.frandom = new TRandom();
		jgg2.frandom = new TRandom();
		//まず初期化
		jgg1.TInitializePop(min, max, popSize, dimension);
		
		System.out.println("jgg1");
		System.out.println(jgg1.toString());
		
		//親をnp個選択
		jgg2.TSelectParents(np, popSize, jgg1.fpopulation);
		System.out.println("jgg2");
		System.out.println(jgg2.toString());
		
		//ktabletで評価
		jgg1.Tktablet(dimension);
		System.out.println("jgg1");
		System.out.println(jgg1.toString());
		
		//ソート
		jgg1.fpopulation.sortByEvaluatel();
		System.out.println("after-sort-jgg1");
		System.out.println(jgg1.toString());
		
		//jgg1からnp個の親を選ぶ
		jgg3.TSelectEVal(np, jgg1.fpopulation);
		
		System.out.println("select-jgg1");
		System.out.println(jgg3.toString());
		
		System.out.println("befor-add-jgg1");
		System.out.println(jgg1.toString());
		//np個の子を加える
		jgg1.TAddChildern(np, popSize, jgg2.fpopulation);
		System.out.println("after-add-jgg1");
		System.out.println(jgg1.toString());
	}
	
	
}
