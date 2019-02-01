/**
 * Copyright (c) 2012-2019 Marsha Chechik, Alessio Di Sandro, Rick Salay.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Alessio Di Sandro - Implementation.
 */
package edu.toronto.cs.se.modelepedia.sosym18.operator;

import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.jdt.annotation.NonNull;

import edu.toronto.cs.se.mmint.MIDTypeRegistry;
import edu.toronto.cs.se.mmint.MMINTException;
import edu.toronto.cs.se.mmint.mid.GenericElement;
import edu.toronto.cs.se.mmint.mid.MID;
import edu.toronto.cs.se.mmint.mid.MIDFactory;
import edu.toronto.cs.se.mmint.mid.MIDLevel;
import edu.toronto.cs.se.mmint.mid.MIDPackage;
import edu.toronto.cs.se.mmint.mid.Model;
import edu.toronto.cs.se.mmint.mid.operator.impl.RandomOperatorImpl;
import edu.toronto.cs.se.mmint.mid.relationship.ModelRel;
import edu.toronto.cs.se.mmint.mid.utils.MIDOperatorIOUtils;

public class SOSYM18 extends RandomOperatorImpl {

  private Output output;
  private final static @NonNull String PROP_IN_POLYTYPEID = "polyTypeId";
  private final static @NonNull String PROP_IN_OTHERTYPEID = "otherTypeId";
  private final static @NonNull String PROP_IN_NUMPOLYTYPES = "numPolyTypes";
  private final static @NonNull String PROP_IN_NUMAPPLICATIONSITES = "numApplicationSites";
  private final static @NonNull String PROP_IN_NUMNONAPPLICATIONSITES = "numNonApplicationSites";
  private final static @NonNull String PROP_IN_MODE = "mode";
  private String polyTypeId;
  private String otherTypeId;
  private int numPolyTypes;
  private int numApplicationSites;
  private int numNonApplicationSites;

  private static class Output {
    private final static @NonNull String OUT_MID = "polyMID";
    private final static @NonNull String POLY_NAME = "poly";
    private final static @NonNull String OTHER_NAME = "other";
    private MID polyMIDModelContainer;
    private Model polyMIDModel;

    public Output(@NonNull Map<String, MID> outputMIDsByName) {
      this.polyMIDModelContainer = outputMIDsByName.get(OUT_MID);
    }

    public @NonNull Map<String, Model> packed() {
      var outputsByName = new HashMap<String, Model>();
      outputsByName.put(OUT_MID, this.polyMIDModel);
      return outputsByName;
    }
  }

  @Override
  public void readInputProperties(Properties inputProperties) throws MMINTException {
    this.polyTypeId = MIDOperatorIOUtils.getStringProperty(inputProperties, PROP_IN_POLYTYPEID);
    this.otherTypeId = MIDOperatorIOUtils.getStringProperty(inputProperties, PROP_IN_OTHERTYPEID);
    this.numPolyTypes = MIDOperatorIOUtils.getIntProperty(inputProperties, PROP_IN_NUMPOLYTYPES);
    this.numApplicationSites = MIDOperatorIOUtils.getIntProperty(inputProperties, PROP_IN_NUMAPPLICATIONSITES);
    this.numNonApplicationSites = MIDOperatorIOUtils.getIntProperty(inputProperties, PROP_IN_NUMNONAPPLICATIONSITES);
  }

  private void init(@NonNull Map<String, Model> inputsByName, @NonNull Map<String, MID> outputMIDsByName) {
      this.output = new Output(outputMIDsByName);
  }

  private Model generateModel(@NonNull Model modelType, @NonNull EFactory modelTypeFactory,
                              @NonNull EClass modelTypeRootObj, @NonNull String modelName,
                              @NonNull MID polyMID) throws Exception {
    var modelPath = Path.of(getInputSubdir(), MessageFormat.format("{0}.{1}", modelName, modelType.getFileExtension()));

    return modelType.createInstance(modelTypeFactory.create(modelTypeRootObj), modelPath.toString(), polyMID);
  }

  private void generateModels(@NonNull MID polyMID) throws Exception {
    // poly types
    var polyModelTypes = new ArrayList<Model>();
    var polyEFactories = new ArrayList<EFactory>();
    var polyEClasses = new ArrayList<EClass>();
    for (var i = 0; i < this.numPolyTypes; i++) {
      Model polyModelType = MIDTypeRegistry.getType(this.polyTypeId + i);
      polyModelTypes.add(polyModelType);
      var polyEPackage = polyModelType.getEMFTypeRoot();
      polyEFactories.add(polyEPackage.getEFactoryInstance());
      polyEClasses.add((EClass) polyEPackage.eContents().get(0));
    }
    var polyIndex = 0;
    for (var i = 0; i < this.numApplicationSites; i++) {
      generateModel(polyModelTypes.get(polyIndex), polyEFactories.get(polyIndex), polyEClasses.get(polyIndex),
                    Output.POLY_NAME + i, polyMID);
      polyIndex = (polyIndex + 1) % this.numPolyTypes;
    }
    // other types
    if (this.numNonApplicationSites == 0) {
      return;
    }
    Model otherModelType = MIDTypeRegistry.getType(this.otherTypeId);
    var otherEPackage = otherModelType.getEMFTypeRoot();
    var otherEFactory = otherEPackage.getEFactoryInstance();
    var otherEClass = (EClass) otherEPackage.eContents().get(0);
    for (int i = 0; i < this.numNonApplicationSites; i++) {
      generateModel(otherModelType, otherEFactory, otherEClass, Output.OTHER_NAME + i, polyMID);
    }
  }

  private ModelRel generateModelRel(@NonNull ModelRel relType, @NonNull Model modelType,
                                    @NonNull EFactory modelTypeFactory, @NonNull EClass modelTypeRootObj,
                                    @NonNull String baseName, @NonNull MID polyMID) throws Exception {
    var models = polyMID.getModels();
    Model model1, model2;
    switch (getState().nextInt(3)) {
    case 1:
      //create new rel between existing models
      model1 = models.get(getState().nextInt(models.size()));
      model2 = models.get(getState().nextInt(models.size()));
      break;
    case 2:
      // create new rel between 1 existing model and 1 new model
      model1 = models.get(getState().nextInt(models.size()));
      model2 = generateModel(modelType, modelTypeFactory, modelTypeRootObj, baseName + "b", polyMID);
      break;
    case 3:
    default:
      // create new rel between 2 new models
      model1 = generateModel(modelType, modelTypeFactory, modelTypeRootObj, baseName + "a", polyMID);
      model2 = generateModel(modelType, modelTypeFactory, modelTypeRootObj, baseName + "b", polyMID);
      break;
    }

    return relType.createInstanceAndEndpoints(null, baseName, ECollections.asEList(model1, model2), polyMID);
  }

  private void generateModelRels(@NonNull MID polyMID) throws Exception {
    // poly types
    var polyRelTypes = new ArrayList<ModelRel>();
    var polyModelTypes = new ArrayList<Model>();
    var polyEFactories = new ArrayList<EFactory>();
    var polyEClasses = new ArrayList<EClass>();
    for (var i = 0; i < this.numPolyTypes; i++) {
      ModelRel polyRelType = MIDTypeRegistry.getType(this.polyTypeId + i);
      polyRelTypes.add(polyRelType);
      var polyModelType = polyRelType.getModelEndpoints().get(0).getTarget();
      polyModelTypes.add(polyModelType);
      var polyEPackage = polyModelType.getEMFTypeRoot();
      polyEFactories.add(polyEPackage.getEFactoryInstance());
      polyEClasses.add((EClass) polyEPackage.eContents().get(0));
    }
    var polyIndex = 0;
    for (var i = 0; i < this.numApplicationSites; i++) {
      generateModelRel(polyRelTypes.get(polyIndex), polyModelTypes.get(polyIndex), polyEFactories.get(polyIndex),
                       polyEClasses.get(polyIndex), Output.POLY_NAME + i, polyMID);
      polyIndex = (polyIndex + 1) % this.numPolyTypes;
    }
    // other types
    if (this.numNonApplicationSites == 0) {
      return;
    }
    ModelRel otherRelType = MIDTypeRegistry.getType(this.otherTypeId);
    var otherModelType = otherRelType.getModelEndpoints().get(0).getTarget();
    var otherEPackage = otherModelType.getEMFTypeRoot();
    var otherEFactory = otherEPackage.getEFactoryInstance();
    var otherEClass = (EClass) otherEPackage.eContents().get(0);
    for (int i = 0; i < this.numNonApplicationSites; i++) {
      generateModelRel(otherRelType, otherModelType, otherEFactory, otherEClass, Output.OTHER_NAME + i, polyMID);
    }
  }

  private void generatePolyMID() throws Exception {
    var polyMID = MIDFactory.eINSTANCE.createMID();
    polyMID.setLevel(MIDLevel.INSTANCES);
    Model polyBaseType = MIDTypeRegistry.getType(this.polyTypeId);
    if (polyBaseType instanceof ModelRel) {
      generateModelRels(polyMID);
    }
    else {
      generateModels(polyMID);
    }
    var polyMIDPath = Path.of(getInputSubdir(), MessageFormat.format("{0}.{1}", Output.POLY_NAME, MIDPackage.eNAME));
    this.output.polyMIDModel = MIDTypeRegistry.getMIDModelType()
      .createInstanceAndEditor(polyMID, polyMIDPath.toString(), this.output.polyMIDModelContainer);
  }

  @Override
  public Map<String, Model> run(Map<String, Model> inputsByName, Map<String, GenericElement> genericsByName,
                                Map<String, MID> outputMIDsByName) throws Exception {
    //TODO should not serialize all the models and polyMID because this.output.polyMIDModelContainer is null within the experiment
    //TODO count and print numModels
    init(inputsByName, outputMIDsByName);
    generatePolyMID();

    return this.output.packed();
  }

}