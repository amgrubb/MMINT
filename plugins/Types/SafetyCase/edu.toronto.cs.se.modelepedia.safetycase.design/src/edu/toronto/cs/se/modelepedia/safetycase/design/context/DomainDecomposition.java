/**
 * Copyright (c) 2012-2020 Alessio Di Sandro, Marsha Chechik.
 * All rights reserved. This program and the accompanying materials are made available under the terms
 * of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alessio Di Sandro - Implementation
 */
package edu.toronto.cs.se.modelepedia.safetycase.design.context;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.sirius.business.api.action.AbstractExternalJavaAction;
import org.eclipse.sirius.business.api.session.SessionManager;
import org.eclipse.sirius.viewpoint.DSemanticDecorator;

import edu.toronto.cs.se.mmint.MMINTException;
import edu.toronto.cs.se.mmint.mid.ui.MIDDialogCancellation;
import edu.toronto.cs.se.mmint.mid.ui.MIDDialogs;
import edu.toronto.cs.se.modelepedia.safetycase.BasicGoal;
import edu.toronto.cs.se.modelepedia.safetycase.ContextualElement;
import edu.toronto.cs.se.modelepedia.safetycase.DecomposableCoreElement;
import edu.toronto.cs.se.modelepedia.safetycase.EnumDomain;
import edu.toronto.cs.se.modelepedia.safetycase.GSNPackage;
import edu.toronto.cs.se.modelepedia.safetycase.Goal;
import edu.toronto.cs.se.modelepedia.safetycase.IntDomain;
import edu.toronto.cs.se.modelepedia.safetycase.Justification;
import edu.toronto.cs.se.modelepedia.safetycase.RealDomain;
import edu.toronto.cs.se.modelepedia.safetycase.SafetyCase;
import edu.toronto.cs.se.modelepedia.safetycase.Strategy;
import edu.toronto.cs.se.modelepedia.safetycase.design.DomainCommand;

public class DomainDecomposition extends AbstractExternalJavaAction {

  @Override
  public boolean canExecute(Collection<? extends EObject> arg0) {
    if (arg0.size() != 1) {
      return false;
    }
    var modelObj = ((DSemanticDecorator) arg0.iterator().next()).getTarget();
    if (modelObj instanceof Goal) {
      return true;
    }
    return false;
  }

  @Override
  public void execute(Collection<? extends EObject> arg0, Map<String, Object> arg1) {
    var goal = (Goal) ((DSemanticDecorator) arg0.iterator().next()).getTarget();
    var sSession = SessionManager.INSTANCE.getSession(goal);
    var sDomain = sSession.getTransactionalEditingDomain();
    sDomain.getCommandStack().execute(new DomainDecompositionCommand(sDomain, goal));
  }

  private class DomainDecompositionCommand extends DomainCommand {
    private Goal decomposed;

    public DomainDecompositionCommand(TransactionalEditingDomain domain, Goal decomposed) {
      super(domain, (SafetyCase) decomposed.eContainer());
      this.decomposed = decomposed;
    }

    private void addContext(DecomposableCoreElement decomposable, ContextualElement contextual) {
      var context = this.factory.createInContextOf();
      context.setContextOf(decomposable);
      context.setContext(contextual);
    }

    private BasicGoal createCompletenessGoal(String id, String description) {
      var goal = this.factory.createBasicGoal();
      addGoal(goal, id, description);

      return goal;
    }

    private Justification createJustification(Strategy strategy, String id, String description) {
      var justification = this.factory.createJustification();
      addArgumentElement(justification, id, description);
      addContext(strategy, justification);
      this.gsnElements.add(justification);

      return justification;
    }

    @Override
    protected void doExecute() {
      try {
        // ask for input
        var title = "Domain Decomposition";
        var numDomains = 0;
        Set<Integer> subDomainTypes = null;
        var domain = createDomain(title, "Insert the domain",
                                  Set.of(GSNPackage.INT_DOMAIN, GSNPackage.REAL_DOMAIN, GSNPackage.ENUM_DOMAIN));
        if (domain instanceof IntDomain || domain instanceof RealDomain) {
          var message = "Insert the number of sub-domains";
          numDomains = Integer.parseInt(MIDDialogs.getStringInput(title, message, null));
          if (numDomains <= 1) {
            throw new MMINTException("A domain must be decomposed into >1 sub-domains");
          }
          if (domain instanceof IntDomain) {
            subDomainTypes = Set.of(GSNPackage.INT_DOMAIN, GSNPackage.ENUM_DOMAIN, GSNPackage.VALUE_DOMAIN);
          }
          else if (domain instanceof RealDomain) {
            subDomainTypes = Set.of(GSNPackage.REAL_DOMAIN);
          }
        }
        else if (domain instanceof EnumDomain) {
          numDomains = ((EnumDomain) domain).getValues().size();
          subDomainTypes = Set.of(GSNPackage.ENUM_DOMAIN, GSNPackage.VALUE_DOMAIN);
        }
        // create domain decomposition strategy + justification + domain subgoals + completeness goal
        var id = this.decomposed.getId();
        var desc = this.decomposed.getDescription();
        var strategyId = "S-" + id;
        var justificationId = "J-" + id;
        var subGoalId = id + "-";
        var compGoalId = id + "-C";
        var strategyDesc = "Decomposition over domain " + domain.toString();
        var justificationDesc = "Every scenario has a corresponding value in the domain " + domain.toString();
        var subGoalDesc = desc + " for sub-domain ";
        var compGoalDesc = "Every possible value in the domain " + domain.toString() + " is covered by sub-domains ";
        var strategy = createDomainStrategy(strategyId, strategyDesc, domain);
        createJustification(strategy, justificationId, justificationDesc);
        var subDomains = new ArrayList<String>();
        for (var i = 0; i < numDomains; i++) {
          var subDomain = createDomain(title, "Insert the sub-domain #" + (i+1), subDomainTypes);
          subDomains.add(subDomain.toString());
          var goal = createDomainGoal(subGoalId + i, subGoalDesc + subDomain.toString(), subDomain);
          addSupporter(strategy, goal);
        }
        var goal = createCompletenessGoal(compGoalId, compGoalDesc + String.join(", ", subDomains));
        addSupporter(strategy, goal);
        // check decomposition validity
        strategy.validate();
        // "commit" the changes
        addSupporter(this.decomposed, strategy);
        commitChanges();
      }
      catch (MIDDialogCancellation e) {
        // do nothing
      }
      catch (Exception e) {
        MMINTException.print(IStatus.ERROR, "Domain decomposition error", e);
      }
    }
  }
}
