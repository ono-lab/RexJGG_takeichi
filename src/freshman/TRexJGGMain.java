package freshman;

public class TRexJGGMain {


	
	public static void main(String[] args) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		

		
		//�ϐ��ݒ�
		double min= -5.0;
		double max= 5.0;
		int dimension = 20; 
		int popSize= 14 * dimension;
		int np = 5 * dimension;
		int k = 2;
		
		//�萔�ݒ�
		double ENDEval = 1.0 * 1e-7;
		long maxEvals = (long)(4 * dimension * 1e4); //�ł��؂�]����
		
		int gene = 0;
		double eval = Double.MAX_VALUE;
		
		
		TJGG jgg1 = new TJGG();
		TJGG jgg2 = new TJGG();
		TJGG jgg3 = new TJGG();

		TRex rex1 = new TRex();


		TPopulation pop1 = new TPopulation();
		pop1.setPopulationSize(np);
		TRandom ran1 = new TRandom();
		jgg1.setRandom(ran1);
		TRandom ran2 = new TRandom();
		jgg2.setRandom(ran2);
		
		//inds[]�𖈉񐶐��@�Ӗ��Ȃ�����
		TIndividual[] inds = new TIndividual[np];
		for(int j=0; j< np; ++j) {
			inds[j] = new TIndividual();
		}

		


		
		//�����W�c����
		jgg1.TInitializePop(min, max, popSize, dimension);
		jgg1.Tktablet(dimension);

		while(gene < maxEvals & eval > ENDEval) {

			
			//�e�̑I��
			jgg2.TSelectParents(np, popSize, jgg1.getPopulation());
			
			//�q�̐���  <�Ȃ񂩂�������>
			for(int i=0; i < np; ++i) {	
				//TVector v1 = new TVector();
				//v1.setDimension(dimension);
				//inds[i].getVector().setDimension(dimension);
				//TIndividual ind1 = new TIndividual();
				//for(int j=0; j < dimension; ++j) {
				//	inds[i].getVector().setElement(j, (rex1.creatRex(jgg2.getPopulation(), dimension, k).getElement(j)));
				//}
				//v1.CopyFrom(rex1.creatRex(jgg2.getPopulation(), dimension, k));
				//inds[i].setVector(v1);
				inds[i].setVector(rex1.creatRex(jgg2.getPopulation(), dimension, k));
				if(gene==0) {
					//System.out.println(inds[i]);
				}
				pop1.setIndividual(i, inds[i]);
			}
			
			if(gene == 0) {
				//System.out.println(pop1);
			}
			
			//�q�̕]��
			jgg3.setPopulation(pop1);
			
			jgg3.Tktablet(dimension);
			pop1.sortByEvaluatel();
			//��Ԃ����]���l���L��
			if(eval > pop1.getIndividual(0).getEvaluate()) {
				eval = pop1.getIndividual(0).getEvaluate();
			}
			//�]���񐔂��X�V
			gene += np;
			
				//�q��e�W�c�ɒǉ�
			jgg1.TAddChildern(np, popSize, jgg3.getPopulation());
			
			//++gene;
			
			if(gene %100 ==0) {
				//System.out.print("gene:" + gene);
				//System.out.println(", eval:"+ eval);
			}
			
		}
		
		System.out.println("eval:" + eval);
		System.out.println("gene:" + gene);
		
	}

}
