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
package edu.toronto.cs.se.modelepedia.kleisli.provider;

import edu.toronto.cs.se.mmint.mid.MidPackage;
import edu.toronto.cs.se.mmint.mid.MultiModel;
import edu.toronto.cs.se.mmint.mid.relationship.ExtendibleElementReference;
import edu.toronto.cs.se.mmint.mid.relationship.ModelRel;
import edu.toronto.cs.se.mmint.mid.relationship.RelationshipPackage;
import edu.toronto.cs.se.mmint.mid.relationship.util.RelationshipSwitch;
import edu.toronto.cs.se.mmint.mid.util.MidSwitch;
import edu.toronto.cs.se.modelepedia.kleisli.KleisliFactory;
import edu.toronto.cs.se.modelepedia.kleisli.util.KleisliAdapterFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ChangeNotifier;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IChangeNotifier;
import org.eclipse.emf.edit.provider.IChildCreationExtender;
import org.eclipse.emf.edit.provider.IDisposable;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.INotifyChangedListener;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;

/**
 * This is the factory that is used to provide the interfaces needed to support Viewers.
 * The adapters generated by this factory convert EMF adapter notifications into calls to {@link #fireNotifyChanged fireNotifyChanged}.
 * The adapters also support Eclipse property sheets.
 * Note that most of the adapters are shared among multiple instances.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class KleisliItemProviderAdapterFactory extends KleisliAdapterFactory implements ComposeableAdapterFactory, IChangeNotifier, IDisposable {
	/**
	 * This keeps track of the root adapter factory that delegates to this adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ComposedAdapterFactory parentAdapterFactory;

	/**
	 * This is used to implement {@link org.eclipse.emf.edit.provider.IChangeNotifier}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IChangeNotifier changeNotifier = new ChangeNotifier();

	/**
	 * This keeps track of all the supported types checked by {@link #isFactoryForType isFactoryForType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Collection<Object> supportedTypes = new ArrayList<Object>();

	/**
	 * This constructs an instance.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public KleisliItemProviderAdapterFactory() {
		supportedTypes.add(IEditingDomainItemProvider.class);
		supportedTypes.add(IStructuredItemContentProvider.class);
		supportedTypes.add(ITreeItemContentProvider.class);
		supportedTypes.add(IItemLabelProvider.class);
		supportedTypes.add(IItemPropertySource.class);
	}

	/**
	 * This keeps track of the one adapter used for all {@link edu.toronto.cs.se.modelepedia.kleisli.KleisliModel} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected KleisliModelItemProvider kleisliModelItemProvider;

	/**
	 * This creates an adapter for a {@link edu.toronto.cs.se.modelepedia.kleisli.KleisliModel}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createKleisliModelAdapter() {
		if (kleisliModelItemProvider == null) {
			kleisliModelItemProvider = new KleisliModelItemProvider(this);
		}

		return kleisliModelItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link edu.toronto.cs.se.modelepedia.kleisli.KleisliModelRel} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected KleisliModelRelItemProvider kleisliModelRelItemProvider;

	/**
	 * This creates an adapter for a {@link edu.toronto.cs.se.modelepedia.kleisli.KleisliModelRel}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createKleisliModelRelAdapter() {
		if (kleisliModelRelItemProvider == null) {
			kleisliModelRelItemProvider = new KleisliModelRelItemProvider(this);
		}

		return kleisliModelRelItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link edu.toronto.cs.se.modelepedia.kleisli.KleisliModelEndpoint} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected KleisliModelEndpointItemProvider kleisliModelEndpointItemProvider;

	/**
	 * This creates an adapter for a {@link edu.toronto.cs.se.modelepedia.kleisli.KleisliModelEndpoint}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createKleisliModelEndpointAdapter() {
		if (kleisliModelEndpointItemProvider == null) {
			kleisliModelEndpointItemProvider = new KleisliModelEndpointItemProvider(this);
		}

		return kleisliModelEndpointItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link edu.toronto.cs.se.modelepedia.kleisli.KleisliBinaryModelRel} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected KleisliBinaryModelRelItemProvider kleisliBinaryModelRelItemProvider;

	/**
	 * This creates an adapter for a {@link edu.toronto.cs.se.modelepedia.kleisli.KleisliBinaryModelRel}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createKleisliBinaryModelRelAdapter() {
		if (kleisliBinaryModelRelItemProvider == null) {
			kleisliBinaryModelRelItemProvider = new KleisliBinaryModelRelItemProvider(this);
		}

		return kleisliBinaryModelRelItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link edu.toronto.cs.se.modelepedia.kleisli.KleisliModelEndpointReference} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected KleisliModelEndpointReferenceItemProvider kleisliModelEndpointReferenceItemProvider;

	/**
	 * This creates an adapter for a {@link edu.toronto.cs.se.modelepedia.kleisli.KleisliModelEndpointReference}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createKleisliModelEndpointReferenceAdapter() {
		if (kleisliModelEndpointReferenceItemProvider == null) {
			kleisliModelEndpointReferenceItemProvider = new KleisliModelEndpointReferenceItemProvider(this);
		}

		return kleisliModelEndpointReferenceItemProvider;
	}

	/**
	 * This returns the root adapter factory that contains this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ComposeableAdapterFactory getRootAdapterFactory() {
		return parentAdapterFactory == null ? this : parentAdapterFactory.getRootAdapterFactory();
	}

	/**
	 * This sets the composed adapter factory that contains this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParentAdapterFactory(ComposedAdapterFactory parentAdapterFactory) {
		this.parentAdapterFactory = parentAdapterFactory;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object type) {
		return supportedTypes.contains(type) || super.isFactoryForType(type);
	}

	/**
	 * This implementation substitutes the factory itself as the key for the adapter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter adapt(Notifier notifier, Object type) {
		return super.adapt(notifier, this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object adapt(Object object, Object type) {
		if (isFactoryForType(type)) {
			Object adapter = super.adapt(object, type);
			if (!(type instanceof Class<?>) || (((Class<?>)type).isInstance(adapter))) {
				return adapter;
			}
		}

		return null;
	}

	/**
	 * This adds a listener.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void addListener(INotifyChangedListener notifyChangedListener) {
		changeNotifier.addListener(notifyChangedListener);
	}

	/**
	 * This removes a listener.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void removeListener(INotifyChangedListener notifyChangedListener) {
		changeNotifier.removeListener(notifyChangedListener);
	}

	/**
	 * This delegates to {@link #changeNotifier} and to {@link #parentAdapterFactory}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void fireNotifyChanged(Notification notification) {
		changeNotifier.fireNotifyChanged(notification);

		if (parentAdapterFactory != null) {
			parentAdapterFactory.fireNotifyChanged(notification);
		}
	}

	/**
	 * This disposes all of the item providers created by this factory. 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void dispose() {
		if (kleisliModelItemProvider != null) kleisliModelItemProvider.dispose();
		if (kleisliModelEndpointItemProvider != null) kleisliModelEndpointItemProvider.dispose();
		if (kleisliModelRelItemProvider != null) kleisliModelRelItemProvider.dispose();
		if (kleisliBinaryModelRelItemProvider != null) kleisliBinaryModelRelItemProvider.dispose();
		if (kleisliModelEndpointReferenceItemProvider != null) kleisliModelEndpointReferenceItemProvider.dispose();
	}

	/**
	 * A child creation extender for the {@link MidPackage}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static class MidChildCreationExtender implements IChildCreationExtender {
		/**
		 * The switch for creating child descriptors specific to each extended class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		protected static class CreationSwitch extends MidSwitch<Object> {
			/**
			 * The child descriptors being populated.
			 * <!-- begin-user-doc -->
			 * <!-- end-user-doc -->
			 * @generated
			 */
			protected List<Object> newChildDescriptors;

			/**
			 * The domain in which to create the children.
			 * <!-- begin-user-doc -->
			 * <!-- end-user-doc -->
			 * @generated
			 */
			protected EditingDomain editingDomain;

			/**
			 * Creates the a switch for populating child descriptors in the given domain.
			 * <!-- begin-user-doc -->
			 * <!-- end-user-doc -->
			 * @generated
			 */
			CreationSwitch(List<Object> newChildDescriptors, EditingDomain editingDomain) {
				this.newChildDescriptors = newChildDescriptors;
				this.editingDomain = editingDomain;
			}
			/**
			 * <!-- begin-user-doc -->
			 * <!-- end-user-doc -->
			 * @generated
			 */
			@Override
			public Object caseMultiModel(MultiModel object) {
				newChildDescriptors.add
					(createChildParameter
						(MidPackage.Literals.MULTI_MODEL__MODELS,
						 KleisliFactory.eINSTANCE.createKleisliModel()));

				newChildDescriptors.add
					(createChildParameter
						(MidPackage.Literals.MULTI_MODEL__MODELS,
						 KleisliFactory.eINSTANCE.createKleisliModelRel()));

				newChildDescriptors.add
					(createChildParameter
						(MidPackage.Literals.MULTI_MODEL__MODELS,
						 KleisliFactory.eINSTANCE.createKleisliBinaryModelRel()));

				return null;
			}
 
			/**
			 * <!-- begin-user-doc -->
			 * <!-- end-user-doc -->
			 * @generated
			 */
			protected CommandParameter createChildParameter(Object feature, Object child) {
				return new CommandParameter(null, feature, child);
			}

		}

		/**
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public Collection<Object> getNewChildDescriptors(Object object, EditingDomain editingDomain) {
			ArrayList<Object> result = new ArrayList<Object>();
			new CreationSwitch(result, editingDomain).doSwitch((EObject)object);
			return result;
		}

		/**
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public ResourceLocator getResourceLocator() {
			return KleisliEditPlugin.INSTANCE;
		}
	}

	/**
	 * A child creation extender for the {@link RelationshipPackage}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static class RelationshipChildCreationExtender implements IChildCreationExtender {
		/**
		 * The switch for creating child descriptors specific to each extended class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		protected static class CreationSwitch extends RelationshipSwitch<Object> {
			/**
			 * The child descriptors being populated.
			 * <!-- begin-user-doc -->
			 * <!-- end-user-doc -->
			 * @generated
			 */
			protected List<Object> newChildDescriptors;

			/**
			 * The domain in which to create the children.
			 * <!-- begin-user-doc -->
			 * <!-- end-user-doc -->
			 * @generated
			 */
			protected EditingDomain editingDomain;

			/**
			 * Creates the a switch for populating child descriptors in the given domain.
			 * <!-- begin-user-doc -->
			 * <!-- end-user-doc -->
			 * @generated
			 */
			CreationSwitch(List<Object> newChildDescriptors, EditingDomain editingDomain) {
				this.newChildDescriptors = newChildDescriptors;
				this.editingDomain = editingDomain;
			}
			/**
			 * <!-- begin-user-doc -->
			 * <!-- end-user-doc -->
			 * @generated
			 */
			@Override
			public Object caseExtendibleElementReference(ExtendibleElementReference object) {
				newChildDescriptors.add
					(createChildParameter
						(RelationshipPackage.Literals.EXTENDIBLE_ELEMENT_REFERENCE__CONTAINED_OBJECT,
						 KleisliFactory.eINSTANCE.createKleisliModel()));

				newChildDescriptors.add
					(createChildParameter
						(RelationshipPackage.Literals.EXTENDIBLE_ELEMENT_REFERENCE__CONTAINED_OBJECT,
						 KleisliFactory.eINSTANCE.createKleisliModelEndpoint()));

				newChildDescriptors.add
					(createChildParameter
						(RelationshipPackage.Literals.EXTENDIBLE_ELEMENT_REFERENCE__CONTAINED_OBJECT,
						 KleisliFactory.eINSTANCE.createKleisliModelRel()));

				newChildDescriptors.add
					(createChildParameter
						(RelationshipPackage.Literals.EXTENDIBLE_ELEMENT_REFERENCE__CONTAINED_OBJECT,
						 KleisliFactory.eINSTANCE.createKleisliBinaryModelRel()));

				return null;
			}
 
			/**
			 * <!-- begin-user-doc -->
			 * <!-- end-user-doc -->
			 * @generated
			 */
			@Override
			public Object caseModelRel(ModelRel object) {
				newChildDescriptors.add
					(createChildParameter
						(RelationshipPackage.Literals.MODEL_REL__MODEL_ENDPOINTS,
						 KleisliFactory.eINSTANCE.createKleisliModelEndpoint()));

				newChildDescriptors.add
					(createChildParameter
						(RelationshipPackage.Literals.MODEL_REL__MODEL_ENDPOINT_REFS,
						 KleisliFactory.eINSTANCE.createKleisliModelEndpointReference()));

				return null;
			}
 
			/**
			 * <!-- begin-user-doc -->
			 * <!-- end-user-doc -->
			 * @generated
			 */
			protected CommandParameter createChildParameter(Object feature, Object child) {
				return new CommandParameter(null, feature, child);
			}

		}

		/**
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public Collection<Object> getNewChildDescriptors(Object object, EditingDomain editingDomain) {
			ArrayList<Object> result = new ArrayList<Object>();
			new CreationSwitch(result, editingDomain).doSwitch((EObject)object);
			return result;
		}

		/**
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public ResourceLocator getResourceLocator() {
			return KleisliEditPlugin.INSTANCE;
		}
	}

}
