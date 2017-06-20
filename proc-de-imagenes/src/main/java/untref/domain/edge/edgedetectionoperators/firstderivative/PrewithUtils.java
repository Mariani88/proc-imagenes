package untref.domain.edge.edgedetectionoperators.firstderivative;


public interface PrewithUtils {

	int[][] PREWITH_OPERATOR_Fx = { { -1, -1, -1 }, { 0, 0, 0 }, { 1, 1, 1 } };
	int[][] PREWITH_OPERATOR_Fy = { { -1, 0, 1 }, { -1, 0, 1 }, { -1, 0, 1 } };
	int OFFSET_I = 1;
	int OFFSET_J = 1;

}
