package freshman;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


//ベクトルのクラス
public class TVector {

	private double [] fData;
	public static double EPS = 1e-10;

	//TVector()のコンストラクタ
	public TVector() {
		fData = new double[0];
	}
	
	public TVector(TVector src) {
		fData = new double[src.fData.length];
		for(int i=0; i < fData.length; ++i) {
			fData[i]= src.fData[i];
		}
	}
	
	//TVectorのコピー
	public TVector CopyFrom(TVector src) {
		if(fData.length != src.fData.length) {
			fData = new double[src.fData.length];
		}
		for(int i= 0; i < fData.length; ++i) {
			fData[i]= src.fData[i];
		}
		return this;
	}
	
	@Override
	public TVector clone() {
		return new TVector(this);
	}
	
	//書き出し
	public void writeTo(PrintWriter pw) {
		pw.println(fData.length);
		for(int i=0; i < fData.length; ++i) {
			pw.print(fData[i] + " " );
		}
		pw.println();
	}
	
	//読み込み
	public void readFrom(BufferedReader br) throws IOException{
		int dimension = Integer.parseInt(br.readLine());
		setDimension(dimension);
		String [] tokens = br.readLine().split(" ");
		for(int i= 0; i < dimension; ++i) {
			fData[i] = Double.parseDouble(tokens[i]);
		}
	}
	
	//次元のset
	public void setDimension(int dimension) {
		if(fData.length != dimension) {
			fData = new double[dimension];
		}
	}
	
	//次元のget
	public int getDimension() {
		return fData.length;
	}
	
	//要素のget
	public double getElement(int index) {
		return fData[index];
	}
	
	//要素のset
	public void setElement(int index, double e) {
		fData[index] = e;
	}
	
	@Override
	public String toString() {
		String str = "";
		for(int i= 0; i < fData.length ; ++i) {
			str += fData[i] + " ";
		}
		return str;
	}
	
	//2つのTVectorが同じか
	@Override
	public boolean equals(Object o) {
		TVector v = (TVector)o; //ダウンキャスト
		assert fData.length == v.fData.length; //アサート コンパイル時 -eで無効
		for(int i=0; i < fData.length; ++i) {
			if(Math.abs(fData[i] - v.fData[i]) > EPS) {
				return false;
			}
		}
		return true;
	}
	
	//要素の加算
	public TVector add(TVector v) {
		assert fData.length == v.fData.length;
		for(int i=0; i < fData.length; ++i) {
			fData[i] += v.fData[i];
		}
		return this;
	}
	
	//要素の減算
	public TVector subtract(TVector v) {
		assert fData.length == v.fData.length;
		for(int i=0; i < fData.length; ++i) {
			fData[i] -= v.fData[i];
		}
		return this;
	}
	
	//スカラー倍する
	public TVector scalarMul(double x) {
		for(int i=0; i < fData.length; ++i) {
			fData[i] *= x;
		}
		return this;
	}
	
	//要素同士の掛け算
	public TVector calMul(TVector v) {
		for(int i=0; i < fData.length; ++i) {
			fData[i] *= v.fData[i];
		}
		return this;
	}
	
	//ノルムの計算
	public double calculateNorm() {
		double norm = 0;
		for(int i=0; i < fData.length; ++i) {
			norm += Math.pow(fData[i],2); //2乗する
		}
		norm = Math.sqrt(norm); //ルート
		return norm;
	}
	
	//正規化
	public TVector normalize() {
		double norm = 0;
		norm = this.calculateNorm();
		for(int i=0 ; i < fData.length; ++i) {
			fData[i] /= norm; 
		}
		return this;
	}
	
	//内積計算
	public double calInnerProduct(TVector v1, TVector v2) {
		double innerProduct = 0;
		for(int i=0; i < v1.fData.length; ++i) {
			innerProduct += v1.fData[i] * v2.fData[i];
		}
		return innerProduct;
	}
	
	
	//外積計算
	public double calOuterProduct(TVector v) {
		
		return 1.0;
	}
	
	
	
	//メイン関数
	public static void main(String []args) {
		
		try {
			TVectorTest();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		
	}
	
	//テストする関数
	public static void TVectorTest() throws IOException{
		TVector v1 = new TVector();
		TVector v2 = new TVector();
		//初期状態のv1確認
		System.out.println("Test1");
		System.out.println(v1);
		System.out.println(v1.getDimension());
		
		v1.setDimension(3);
		v2.setDimension(3);
		
		//v1に値を代入
		for(int i=0; i< v1.fData.length; ++i) {
			v1.fData[i] = i*5;
		}
		//v2に値を代入
		for(int i=0; i< v1.fData.length; ++i) {
			v2.fData[i] = i + 3;
		}
			
		//代入後のv1,v2を確認
		System.out.println("\n" + "Test2");
		System.out.println("v1: "+ v1 + ", v2 :" + v2);
		System.out.println("v1_D: " + v1.getDimension() + ", v2_D: " + v2.getDimension());
		//加算・減算確認 ← v2加算後のv1からv2を減算しているのでv1に戻る
		System.out.println("v1+v2: " + v1.add(v2) + ", v1-v2 :" + v1.subtract(v2));
		//ついでにスカラー倍も確認
		System.out.println("v1*5: " + v1.scalarMul(5));
		//ノルム、正規化確認
		System.out.println("v1_norm :" + v1.calculateNorm() + ", v1_normalize :" + v1.normalize());
		System.out.println("v2_norm :" + v2.calculateNorm() + ", v2_normalize :" + v2.normalize());

		System.out.println("\n" + "Test3");
		
		//v1,v2に値を代入
		for(int i=0; i< v1.fData.length; ++i) {
			v1.fData[i] = i;
		}
		for(int i=0; i< v2.fData.length; ++i) {
			v2.fData[i] = i;
		}
		
		//v1,v2が一致しているか確認
		System.out.println("v1=v2?: " + v1.equals(v2));
		//v1[0],v2[0]に値を代入
		v1.setElement(0,0.333333333);
		v2.setElement(0, 1.0/3.0);
		System.out.println("v1=v2?: " + v1.equals(v2)); 
		System.out.println("v1: "+ v1 + ", v2 :" + v2);
		v1.setElement(0,0.333333333333);   //EPS以下にv1[0]を設定すれば一致
		System.out.println("v1=v2?: " + v1.equals(v2));
		System.out.println("v1: "+ v1 + ", v2 :" + v2);
		
		//読み込み・書き出し
			try {
				System.out.println("\n" + "Test4");
				v1.setElement(0,0);
				String str = v1.toString();
				System.out.println("v1:" + str);
					
				File filename = new File("test_TVector.txt"); 
				filename.createNewFile();
				

				//FileWriterオブジェクトの生成
				FileWriter fw = new FileWriter(filename);

				//BufferedWriterオブジェクトの生成
				BufferedWriter bw = new BufferedWriter(fw);

				//PrintWriterオブジェクトの生成
				PrintWriter pw = new PrintWriter(bw);
				v1.writeTo(pw);
				pw.close();
				
				//v2の内容をv1で書き換え
				BufferedReader br = new BufferedReader(new FileReader(filename)); 
				v2.readFrom(br); 
				br.close(); 
				System.out.println("v1: "+ v1 + ", v2 :" + v2);
				
			}catch(IOException e) { 
		 		System.out.println(e); 
			} 

	}
	
}
