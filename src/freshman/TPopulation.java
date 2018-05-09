package freshman;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class TPopulation {

	private TIndividual[] fIndividuals;
	
	/**
	 * コンストラクタ
	 */
	public TPopulation(){
		setfIndividuals(new TIndividual[0]);
	}
	
	/**
	 * コンストラクタ
	 * @param popSize
	 */
	public  TPopulation(int popSize) {
		setfIndividuals(new TIndividual[popSize]);
		for(int i= 0; i < getfIndividuals().length; ++i) {
			getfIndividuals()[i] = new TIndividual();
		}
	}
	
	public TPopulation(TPopulation src) {
		setfIndividuals(new TIndividual[src.getfIndividuals().length]);
		for(int i=0; i < getfIndividuals().length ; ++i) {
			getfIndividuals()[i] = new TIndividual(src.getfIndividuals()[i]);
		}
	}
	
	@Override
	public TPopulation clone() {
		return new TPopulation(this);
	}
	
	//set,get関係
	public void setPopulationSize(int popSize) {
		if(getfIndividuals().length != popSize) {
			setfIndividuals(new TIndividual[popSize]);
			for(int i=0; i < getfIndividuals().length; ++i) {
				getfIndividuals()[i] = new TIndividual();
			}
		}
	}
	
	public int getPopulationSize() {
		return getfIndividuals().length;
	}
	
	public TIndividual getIndividual(int index) {
		return fIndividuals[index];
	}
	
	public void setIndividual(int index, TIndividual findividual) {
		if(getfIndividuals().length < index ) {
			System.out.println("Eroor index");
		}		
			fIndividuals[index] = findividual;
		
	}
	
	
	public TPopulation copyFrom(TPopulation src) {
		setPopulationSize(src.getfIndividuals().length);
		for(int i=0; i < getfIndividuals().length; ++i) {
			getfIndividuals()[i].CopyFrom(src.getfIndividuals()[i]);
		}
		return this;
	}
	
	
	//writer reader
	public void writeTo(PrintWriter pw) {
		pw.println(getfIndividuals().length);
		for(int i=0; i< getfIndividuals().length; ++i) {
			getfIndividuals()[i].writeTo(pw);
		}
	}
	
	public void readFrom(BufferedReader br) throws IOException{
		int popSize = Integer.parseInt(br.readLine());
		setPopulationSize(popSize);
		for(int i=0; i < getfIndividuals().length ; ++i) {
			getfIndividuals()[i].readFrom(br);
		}
	}
	
	@Override
	public String toString() {
		String str = getPopulationSize() + "\n";
		for(int i=0; i < getPopulationSize(); ++i) {
			str += getfIndividuals()[i].toString() + "\n";
		}
		return str;
	}
	
	/**
	 * 評価値でソート
	 * @param args
	 */
	public void sortByEvaluatel() {
		//とりあえずバブルソート
		for(int i=0; i < getPopulationSize() -1; ++i) {
			for(int j=i; j < getPopulationSize()-1; ++j) {
				if(getfIndividuals()[i].getEvaluate() > getfIndividuals()[j].getEvaluate()) {
					TIndividual tmp = getfIndividuals()[i];
					getfIndividuals()[i] = getfIndividuals()[j];
					getfIndividuals()[j] = tmp;
				}
			}
		}
	}
	
	
	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		TPopulationTest();
	}

	/**
	 * TPpolationのテスト関数
	 */
	public static void TPopulationTest() {
		
		TPopulation src = new TPopulation();
		//初期状態のsrc確認
		System.out.println("Test1");
		System.out.println(src.getfIndividuals().length);
		System.out.println(src);
		
		//individualに要素をセット
		System.out.println("\n" + "Test2");
		TVector v1 = new TVector();
		TVector v2 = new TVector();
		TVector v3 = new TVector();
		v1.setDimension(3);
		v2.setDimension(3);
		v3.setDimension(3);
		TIndividual ind1 = new TIndividual();
		TIndividual ind2 = new TIndividual();
		TIndividual ind3 = new TIndividual();
		
		//v1に値を代入
		for(int i=0; i< v1.getDimension(); ++i) {
			v1.setElement(i, i+1);
		}
		//v2に値を代入
		for(int i=0; i< v1.getDimension(); ++i) {
			v2.setElement(i, i+2);
		}
		//v3に値を代入
		for(int i=0; i< v1.getDimension(); ++i) {
			v3.setElement(i, i+3);
		}
		
		ind1.setEvaluate(2.0);
		ind2.setEvaluate(1.0);
		ind3.setEvaluate(3.0);
		ind1.setVector(v1);
		ind2.setVector(v2);
		ind3.setVector(v3);
		
		src.setPopulationSize(3);
		src.getfIndividuals()[0] = ind1;
		src.getfIndividuals()[1] = ind2;
		src.getfIndividuals()[2] = ind3;
		
		//set後のsrc確認
		System.out.println(src.getfIndividuals().length);
		System.out.println(src.getIndividual(0)); 
		System.out.println(src.getIndividual(1)); 
		System.out.println(src.getIndividual(2)); 
		
		//src2にコピー
		System.out.println("\n" + "Test3");
		TPopulation src2 = new TPopulation();
		src2.copyFrom(src);
		System.out.println("src:" + src + "\n" + "src2:" + src2);
		
		//ソート確認
		src.sortByEvaluatel();
		System.out.println(src);
		
		System.out.println("\n" + "Test4");
		//src2の評価値を0、ベクトルをv1に変更
		for(int i=0; i<3; ++i) {
			src2.getfIndividuals()[i].setEvaluate(0.0);
			src2.getfIndividuals()[i].setVector(v1);
		}
		System.out.println("src:" + src + "\n" + "src2:" + src2);

		//src2をすべて個体ind3に変更
		src2.setIndividual(0,ind3);
		src2.setIndividual(1,ind3);
		src2.setIndividual(2,ind3);
		System.out.println("src:" + src + "\n" + "src2:" + src2);
		
		//読み込み・書き出し
		try {
				
			File filename = new File("test_TPopulation.txt"); 
			filename.createNewFile();
			

			//FileWriterオブジェクトの生成
			FileWriter fw = new FileWriter(filename);

			//BufferedWriterオブジェクトの生成
			BufferedWriter bw = new BufferedWriter(fw);

			//PrintWriterオブジェクトの生成
			PrintWriter pw = new PrintWriter(bw);
			src.writeTo(pw);
			pw.close();
			
			//src2の内容をsrcで書き換え
			BufferedReader br = new BufferedReader(new FileReader(filename)); 
			src2.readFrom(br); 
			br.close(); 
			System.out.println("\n" + "Test5");
			System.out.println("src: "+ src + "\n" + "src2 :" + src2);
			
		}catch(IOException e) { 
	 		System.out.println(e); 
		} 
	}

	public TIndividual[] getfIndividuals() {
		return fIndividuals;
	}

	public void setfIndividuals(TIndividual[] fIndividuals) {
		this.fIndividuals = fIndividuals;
	}

	
}
