/**
 * Copyright (c) 2012-2014 Marsha Chechik, Alessio Di Sandro, Michalis Famelis,
 * Rick Salay.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Alessio Di Sandro - Implementation.
 */
package edu.toronto.cs.se.modelepedia.graph_mavo.operator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.eclipse.emf.common.util.EList;

import com.microsoft.z3.FuncDecl;
import com.microsoft.z3.FuncInterp;
import com.microsoft.z3.Z3Exception;
import com.microsoft.z3.FuncInterp.Entry;

import edu.toronto.cs.se.mmint.MMINTException;
import edu.toronto.cs.se.mmint.MultiModelTypeRegistry;
import edu.toronto.cs.se.mmint.mavo.MAVOElement;
import edu.toronto.cs.se.mmint.mid.Model;
import edu.toronto.cs.se.mmint.mid.constraint.MultiModelConstraintChecker.MAVOTruthValue;
import edu.toronto.cs.se.mmint.mid.library.MultiModelOperatorUtils;
import edu.toronto.cs.se.mmint.mid.operator.Operator;
import edu.toronto.cs.se.mmint.mid.operator.impl.RandomOperatorImpl;
import edu.toronto.cs.se.modelepedia.graph_mavo.Edge;
import edu.toronto.cs.se.modelepedia.graph_mavo.Node;
import edu.toronto.cs.se.modelepedia.z3.Z3SMTModel;
import edu.toronto.cs.se.modelepedia.z3.Z3SMTIncrementalSolver;
import edu.toronto.cs.se.modelepedia.z3.Z3SMTIncrementalSolver.Z3IncrementalBehavior;
import edu.toronto.cs.se.modelepedia.z3.Z3SMTUtils;
import edu.toronto.cs.se.modelepedia.z3.Z3SMTModel.Z3SMTBool;
import edu.toronto.cs.se.modelepedia.z3.mavo.EcoreMAVOToSMTLIB;
import edu.toronto.cs.se.modelepedia.z3.reasoning.Z3SMTReasoningEngine;

public class TOSEM12 extends RandomOperatorImpl {

	private static final String PREVIOUS_OPERATOR_URI = "http://se.cs.toronto.edu/modelepedia/Operator_EcoreMAVOToSMTLIB";
	private static final String PREVIOUS_OPERATOR2_URI = "http://se.cs.toronto.edu/modelepedia/Operator_GenerateRandomGraphMAVO";
	private static final String PROPERTY_IN_NUMCONCRETIZATIONS = "numConcretizations";
	private static final String PROPERTY_IN_PROPERTYID = "propertyId";
	private static final String PROPERTY_OUT_TIMEMAVO = "timeMAVO";
	private static final String PROPERTY_OUT_TIMECLASSICAL = "timeClassical";
	private static final String PROPERTY_OUT_TIMEMAVOBACKBONE = "timeMAVOBackbone";
	private static final String PROPERTY_OUT_TIMEMAVOALLSAT = "timeMAVOAllsat";
	private static final String PROPERTY_OUT_SPEEDUPCLASSICALMAVO = "speedupClassicalMAVO";
	private static final String PROPERTY_OUT_SPEEDUPMAVOALLSATMAVOBACKBONE = "speedupMAVOAllsatMAVOBackbone";

	private int numConcretizations;
	private int propertyId;
	private boolean timeClassicalEnabled;
	private boolean timeMAVOBackboneEnabled;
	private boolean timeMAVOAllsatEnabled;
	private MAVOTruthValue resultMAVO;
	private List<MAVOElement> mayModelObjs;
	private String smtEncoding;
	private String smtConcretizationsConstraint;
	private Set<String> smtConcretizations;
	private String smtProperty;
	private long timeMAVO;
	private long timeClassical;
	private long timeMAVOBackbone;
	private long timeMAVOAllsat;
	private double speedupClassicalMAVO;
	private double speedupMAVOAllsatMAVOBackbone;

	@Override
	public void readInputProperties(Properties inputProperties) throws MMINTException {

		numConcretizations = MultiModelOperatorUtils.getIntProperty(inputProperties, PROPERTY_IN_NUMCONCRETIZATIONS);
		propertyId = MultiModelOperatorUtils.getIntProperty(inputProperties, PROPERTY_IN_PROPERTYID);
		timeClassicalEnabled = MultiModelOperatorUtils.getBoolProperty(inputProperties, PROPERTY_OUT_TIMECLASSICAL+MultiModelOperatorUtils.PROPERTY_IN_OUTPUTENABLED_SUFFIX);
		timeMAVOBackboneEnabled = MultiModelOperatorUtils.getBoolProperty(inputProperties, PROPERTY_OUT_TIMEMAVOBACKBONE+MultiModelOperatorUtils.PROPERTY_IN_OUTPUTENABLED_SUFFIX);
		timeMAVOAllsatEnabled = MultiModelOperatorUtils.getBoolProperty(inputProperties, PROPERTY_OUT_TIMEMAVOALLSAT+MultiModelOperatorUtils.PROPERTY_IN_OUTPUTENABLED_SUFFIX);
	}

	@Override
	public void init() throws MMINTException {

		// state
		EcoreMAVOToSMTLIB previousOperator = (getPreviousOperator() == null) ?
			(EcoreMAVOToSMTLIB) MultiModelTypeRegistry.<Operator>getType(PREVIOUS_OPERATOR_URI) :
			(EcoreMAVOToSMTLIB) getPreviousOperator();
		GenerateRandomGraphMAVO previousOperator2 = (GenerateRandomGraphMAVO) MultiModelTypeRegistry.<Operator>getType(PREVIOUS_OPERATOR2_URI);
		resultMAVO = MAVOTruthValue.ERROR;
		mayModelObjs = previousOperator2.getMAVOModelObjects();
		smtEncoding = previousOperator.getListener().getSMTLIBEncoding();
		smtConcretizationsConstraint = "";
		smtConcretizations = new HashSet<String>();
		smtProperty = "";

		// output
		timeMAVO = -1;
		timeClassical = -1;
		timeMAVOBackbone = -1;
		timeMAVOAllsat = -1;
		speedupClassicalMAVO = -1;
		speedupMAVOAllsatMAVOBackbone = -1;
	}

	private void writeProperties(Properties properties) {

		properties.setProperty(PROPERTY_OUT_TIMEMAVO, String.valueOf(timeMAVO));
		properties.setProperty(PROPERTY_OUT_TIMECLASSICAL, String.valueOf(timeClassical));
		properties.setProperty(PROPERTY_OUT_TIMEMAVOBACKBONE, String.valueOf(timeMAVOBackbone));
		properties.setProperty(PROPERTY_OUT_TIMEMAVOALLSAT, String.valueOf(timeMAVOAllsat));
		properties.setProperty(PROPERTY_OUT_SPEEDUPCLASSICALMAVO, String.valueOf(speedupClassicalMAVO));
		properties.setProperty(PROPERTY_OUT_SPEEDUPMAVOALLSATMAVOBACKBONE, String.valueOf(speedupMAVOAllsatMAVOBackbone));
	}

	private String generateSMTLIBConcretization() {

		String smtConcretization = "";
		Map<String, Boolean> wellFormedModelObjs = new HashMap<String, Boolean>();
		for (MAVOElement mayModelObj : mayModelObjs) {
			String mayModelObjSmtEncoding = (mayModelObj instanceof Node) ?
				Z3SMTUtils.predicate(Z3SMTUtils.SMTLIB_NODE_FUNCTION, mayModelObj.getFormulaId()) :
				Z3SMTUtils.predicate(Z3SMTUtils.SMTLIB_EDGE_FUNCTION, mayModelObj.getFormulaId());
			Boolean exists = wellFormedModelObjs.get(mayModelObjSmtEncoding);
			if (exists == null) {
				exists = state.nextBoolean();
				if (mayModelObj instanceof Node) {
					if (!exists) { // enforce future well-formedness
						for (Edge edgeAsSrc : ((Node) mayModelObj).getEdgesAsSource()) {
							wellFormedModelObjs.put(
								Z3SMTUtils.predicate(Z3SMTUtils.SMTLIB_EDGE_FUNCTION, edgeAsSrc.getFormulaId()),
								new Boolean(false)
							);
						}
						for (Edge edgeAsTgt : ((Node) mayModelObj).getEdgesAsTarget()) {
							wellFormedModelObjs.put(
								Z3SMTUtils.predicate(Z3SMTUtils.SMTLIB_EDGE_FUNCTION, edgeAsTgt.getFormulaId()),
								new Boolean(false)
							);
						}
					}
				}
				else {
					if (exists) { // enforce future well-formedness
						wellFormedModelObjs.put(
							Z3SMTUtils.predicate(Z3SMTUtils.SMTLIB_NODE_FUNCTION, ((Edge) mayModelObj).getSource().getFormulaId()),
							new Boolean(true)
						);
						wellFormedModelObjs.put(
							Z3SMTUtils.predicate(Z3SMTUtils.SMTLIB_NODE_FUNCTION, ((Edge) mayModelObj).getTarget().getFormulaId()),
							new Boolean(true)
						);
					}
				}
			}
			else { // well-formedness enforced
				wellFormedModelObjs.remove(mayModelObjSmtEncoding);
			}
			smtConcretization += (exists) ? mayModelObjSmtEncoding: Z3SMTUtils.not(mayModelObjSmtEncoding);
		}
		smtConcretization = Z3SMTUtils.and(smtConcretization);

		return smtConcretization;
	}

	private void generateSMTLIBConcretizations() throws MMINTException {

		long maxConcretizations = Math.round(Math.pow(2, mayModelObjs.size()));
		if (numConcretizations > maxConcretizations) {
			throw new MMINTException("numConcretizations (" + numConcretizations + ") > maxConcretizations (" + maxConcretizations + ")");
		}
		if (maxConcretizations <= 1) {
			return;
		}

		//TODO MMINT[TOSEM] add heuristics to detect large number of concretizations (when it's more efficient to generate them all and then cut some)
		for (int i = 0; i < numConcretizations; i++) {
			String smtConcretization = generateSMTLIBConcretization();
			if (smtConcretizations.contains(smtConcretization)) { // duplicate
				i--;
				continue;
			}
			else {
				smtConcretizations.add(smtConcretization);
				smtConcretizationsConstraint += smtConcretization + '\n';
			}
		}
		smtConcretizationsConstraint = Z3SMTUtils.or(smtConcretizationsConstraint);
	}

	private void generateSMTLIBGroundedProperty() {

		//TODO generate smtProperty (not asserted)
		smtProperty = "(edge e1)";
	}

	private void doMAVOPropertyCheck() {

		long startTime = System.nanoTime();
		resultMAVO = Z3SMTReasoningEngine.checkMAVOProperty(smtEncoding + Z3SMTUtils.assertion(smtConcretizationsConstraint), smtProperty);
		long endTime = System.nanoTime();

		timeMAVO = endTime - startTime;
	}

	private void doClassicalPropertyCheck() {

		long startTime = System.nanoTime();
		Z3SMTIncrementalSolver z3IncSolver = new Z3SMTIncrementalSolver();
		Z3SMTBool firstZ3Bool = null;
		z3IncSolver.firstCheckSatAndGetModel(smtEncoding);
		for (String smtConcretization : smtConcretizations) {
			Z3SMTModel z3Model = z3IncSolver.checkSatAndGetModel(Z3SMTUtils.assertion(smtConcretization) + Z3SMTUtils.assertion(smtProperty), Z3IncrementalBehavior.POP);
			Z3SMTBool z3Bool = z3Model.getZ3Bool();
			if (firstZ3Bool == null) { // first run only
				firstZ3Bool = z3Bool;
			}
			if (z3Bool == Z3SMTBool.UNKNOWN || z3Bool != firstZ3Bool) { // == result never changes
				break;
			}
		}
		long endTime = System.nanoTime();

		timeClassical = endTime - startTime;
	}

	private void doMAVOBackbonePropertyCheck() throws MMINTException, Z3Exception {

		long startTime = System.nanoTime();
		Z3SMTIncrementalSolver z3IncSolver = new Z3SMTIncrementalSolver();
		Z3SMTModel z3Model = z3IncSolver.firstCheckSatAndGetModel(smtEncoding + Z3SMTUtils.assertion(smtConcretizationsConstraint) + Z3SMTUtils.assertion(smtProperty));
		if (z3Model.getZ3Bool() != Z3SMTBool.SAT) {
			throw new MMINTException("MAVO Property checking was SAT but now backbone baseline is not.");
		}
		Map<String, Integer> initialZ3ModelElems = parseZ3Model(z3Model.getZ3InternalModel());
		long endTime = System.nanoTime();

		timeMAVOBackbone = endTime - startTime;
	}

	private Map<String, Integer> parseZ3Model(com.microsoft.z3.Model z3Model) throws Z3Exception {

		/*TODO put this in Z3SMTUtils:
		 * apply to edges as well
		 * the else continue needs to be thought (depends if the result contains only true elements)
		 * the entries won't have the sort concretization getArgs()[1], but only the integer, to be unified
		 */
		Map<String, Integer> z3ModelNodes = new HashMap<String, Integer>();
		for (FuncDecl decl : z3Model.getFuncDecls()) {
			if (!(decl.getName().toString().equals(Z3SMTUtils.SMTLIB_NODE) || decl.getName().toString().contains(Z3SMTUtils.SMTLIB_NODE+Z3SMTUtils.Z3_MODEL_SEPARATOR))) {
				continue;
			}
			FuncInterp interp = z3Model.getFuncInterp(decl);
			if (interp.getEntries().length == 0) {// function that calls another function
				continue;
			}
			if (Boolean.parseBoolean(interp.getElse().toString())) { // nodes will be false
				continue;
			}
			for (Entry entry : interp.getEntries()) {
				z3ModelNodes.put(entry.getArgs()[1].toString(), new Integer(entry.getArgs()[0].toString()));
			}
		}

		return z3ModelNodes;
	}

	@Override
	public EList<Model> execute(EList<Model> actualParameters) throws Exception {

		Model randomGraphModel = actualParameters.get(0);
		generateSMTLIBConcretizations();
		generateSMTLIBGroundedProperty();

		doMAVOPropertyCheck();
		if (timeClassicalEnabled) {
			doClassicalPropertyCheck();
			speedupClassicalMAVO = ((double) timeClassical) / timeMAVO;
		}
		if (resultMAVO == MAVOTruthValue.MAYBE) {
			if (timeMAVOBackboneEnabled) {
				doMAVOBackbonePropertyCheck();
			}
		}

		return null;
	}

}
