package freshman;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

//�̂̃N���X
public class TIndividual {

	private double fEvaluate;		//+������Ŏ��s�s�\
	private TVector fvector;
	
	//�R���X�g���N�^
	public TIndividual() {
		fEvaluate  = Double.NaN;
		fvector = new TVector();
	}
	
	public TIndividual(TIndividual src) {
		fEvaluate = src.fEvaluate;
		fvector = new TVector(src.fvector);
	}
	
	@Override
	public TIndividual clone() {
		return new TIndividual(this);
	}
	
	//TIndividual�̃R�s�[
	public TIndividual CopyFrom(TIndividual src) {
		fEvaluate = src.fEvaluate;
		fvector = src.fvector;
		return this;
	}
	
	public void WriteTo(PrintWriter pw) {
		pw.println(fEvaluate);
		fvector.WriteTo(pw);
	}
	
	public void ReadFrom(BufferedReader br)  throws IOException{
		fEvaluate = Double.parseDouble(br.readLine());
		fvector.ReadFrom(br);
	}
	
	@Override
	public String toString() {
		String str = fEvaluate + "\n";
		str += fvector.toString();
		return str;
	}
	
	public void setEvaluate(double eval) {
		fEvaluate = eval;
	}
	
	public double getEvaluate(TIndividual src) {
		return src.fEvaluate;
	}
	
	public void setVector(TVector v) {
		fvector = v;
	}
	
	public TVector getVector() {
		return fvector;
	}
	
	//���C���֐�
	public static void main(String []args) {
		TIndividualTest();
	}
	
	
	//�e�X�g����֐�
	public static void TIndividualTest() {
		TIndividual src = new TIndividual();
		//������Ԃ�src�m�F
		System.out.println("Test1");
		System.out.println(src);
		System.out.println(src.getVector());
		
		System.out.println("\n" + "Test2");
		TVector v1 = new TVector();
		v1.setDimension(3);
		
		//v1�ɒl����
		for(int i=0; i< v1.getDimension(); ++i) {
			v1.setElement(i, i*5);
		}
		
		src.setEvaluate(1.0);
		src.setVector(v1);
		//set���src�m�F
		System.out.println(src);
		System.out.println(src.getVector()); //�Ȃ����x�N�g���v�f��2�s�\�������E�E�E
		System.out.println(src.toString());  //���������Ƃ�����1�s�ŕ\�������
		
		System.out.println("\n" + "Test3");
		TIndividual src2 = new TIndividual();
		src2.CopyFrom(src);
		System.out.println("src:" + src + "\n" + "src2:" + src2);
		
		src2.setEvaluate(3.0);
		src2.setVector(src2.fvector.ScalorMul(2.0));
		System.out.println("src2 << Evaluate*3,Vector*2");
		System.out.println("src:" + src + "\n" + "src2:" + src2); //�󂢃R�s�[�Ȃ̂�src,src2�̃x�N�g����2�{�����
		
		//�ǂݍ��݁E�����o��
		try {
				
			File filename = new File("test_TIndicidual.txt"); 
			filename.createNewFile();
			

			//FileWriter�I�u�W�F�N�g�̐���
			FileWriter fw = new FileWriter(filename);

			//BufferedWriter�I�u�W�F�N�g�̐���
			BufferedWriter bw = new BufferedWriter(fw);

			//PrintWriter�I�u�W�F�N�g�̐���
			PrintWriter pw = new PrintWriter(bw);
			src.WriteTo(pw);
			pw.close();
			
			//v2�̓��e��v1�ŏ�������
			BufferedReader br = new BufferedReader(new FileReader(filename)); 
			src2.ReadFrom(br); 
			br.close(); 
			System.out.println("\n" + "Test4");
			System.out.println("src: "+ src + "\n" + "src2 :" + src2);
			
		}catch(IOException e) { 
	 		System.out.println(e); 
		} 
		
		
		
	}
	
}
