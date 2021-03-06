/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package fr.obeo.dsl.debug.ide.sirius.ui.services;

import fr.obeo.dsl.debug.StackFrame;
import fr.obeo.dsl.debug.ide.DSLBreakpoint;
import fr.obeo.dsl.debug.ide.adapter.IDSLCurrentInstructionListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IBreakpointListener;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.sirius.business.api.dialect.DialectManager;
import org.eclipse.sirius.business.api.dialect.command.RefreshRepresentationsCommand;
import org.eclipse.sirius.diagram.DDiagram;
import org.eclipse.sirius.diagram.description.Layer;
import org.eclipse.sirius.ui.business.api.dialect.DialectEditor;
import org.eclipse.sirius.ui.business.api.session.IEditingSession;
import org.eclipse.sirius.ui.business.api.session.SessionUIManager;
import org.eclipse.sirius.viewpoint.DRepresentation;
import org.eclipse.sirius.viewpoint.description.RepresentationDescription;

/**
 * DSL debugger services class.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public abstract class AbstractDSLDebuggerServices {

	/**
	 * A couple of {@link String}.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	public static final class StringCouple {

		/**
		 * The first string of the couple.
		 */
		private final String first;

		/**
		 * The second string of the couple.
		 */
		private final String second;

		/**
		 * Constructor.
		 * 
		 * @param first
		 *            the first string of the couple
		 * @param second
		 *            the second string of the couple
		 */
		public StringCouple(String first, String second) {
			this.first = first;
			this.second = second;
		}

		/**
		 * Gets the first string of the couple.
		 * 
		 * @return the first string of the couple
		 */
		public String getFirst() {
			return first;
		}

		/**
		 * Gets the second string of the couple.
		 * 
		 * @return the second string of the couple
		 */
		public String getSecond() {
			return second;
		}
	}

	/**
	 * An {@link IBreakpointListener} maintaining the breakpoints.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	public static final class BreakpointListener implements IBreakpointListener, IDSLCurrentInstructionListener {

		/**
		 * Any layer {@link Set}, means always refresh the given {@link DRepresentation} no matter what its
		 * layer are. It should be used for trees and tables since they don't have layers.
		 */
		private static final Set<String> ANY_LAYER = new HashSet<String>();

		/**
		 * Mapping of the {@link RepresentationDescription#getName() representation identifier} to a
		 * {@link Layer#getName() layer identifier} or {@link BreakpointListener#ANY_LAYER any layer}.
		 */
		private final Map<String, Map<String, Set<String>>> representationToRefresh = new HashMap<String, Map<String, Set<String>>>();

		/**
		 * The current {@link StackFrame}.
		 */
		private StackFrame currentFrame;

		/**
		 * Installs this {@link IBreakpointListener}.
		 */
		public void install() {
			DebugPlugin.getDefault().getBreakpointManager().addBreakpointListener(this);
			for (IBreakpoint breakpoint : DebugPlugin.getDefault().getBreakpointManager().getBreakpoints()) {
				if (breakpoint instanceof DSLBreakpoint) {
					addBreakpoint((DSLBreakpoint)breakpoint);
				}
			}
		}

		/**
		 * Uninstalls this {@link IBreakpointListener}.
		 */
		public void uninstall() {
			DebugPlugin.getDefault().getBreakpointManager().removeBreakpointListener(this);
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.debug.core.IBreakpointListener#breakpointAdded(org.eclipse.debug.core.model.IBreakpoint)
		 */
		public void breakpointAdded(IBreakpoint breakpoint) {
			if (breakpoint instanceof DSLBreakpoint) {
				addBreakpoint((DSLBreakpoint)breakpoint);
				final DSLBreakpoint dslBreakpoint = (DSLBreakpoint)breakpoint;
				notifySirius(dslBreakpoint.getURI(), dslBreakpoint.getModelIdentifier());
			}
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.debug.core.IBreakpointListener#breakpointRemoved(org.eclipse.debug.core.model.IBreakpoint,
		 *      org.eclipse.core.resources.IMarkerDelta)
		 */
		public void breakpointRemoved(IBreakpoint breakpoint, IMarkerDelta delta) {
			if (breakpoint instanceof DSLBreakpoint) {
				removeBreakpoint((DSLBreakpoint)breakpoint);
				final DSLBreakpoint dslBreakpoint = (DSLBreakpoint)breakpoint;
				notifySirius(dslBreakpoint.getURI(), dslBreakpoint.getModelIdentifier());
			}
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.debug.core.IBreakpointListener#breakpointChanged(org.eclipse.debug.core.model.IBreakpoint,
		 *      org.eclipse.core.resources.IMarkerDelta)
		 */
		public void breakpointChanged(IBreakpoint breakpoint, IMarkerDelta delta) {
			try {
				if (breakpoint instanceof DSLBreakpoint
						&& delta.getAttribute(IBreakpoint.ENABLED) != null
						&& breakpoint.isEnabled() != ((Boolean)delta.getAttribute(IBreakpoint.ENABLED))
								.booleanValue()) {
					final DSLBreakpoint dslBreakpoint = (DSLBreakpoint)breakpoint;
					notifySirius(dslBreakpoint.getURI(), dslBreakpoint.getModelIdentifier());
				}
			} catch (CoreException e) {
				// ignore
			}
		}

		/**
		 * Adds the given {@link DSLBreakpoint}.
		 * 
		 * @param breakpoint
		 *            the {@link DSLBreakpoint}
		 */
		protected void addBreakpoint(DSLBreakpoint breakpoint) {
			Set<DSLBreakpoint> brkps = BREAKPOINTS.get(breakpoint.getURI());
			if (brkps == null) {
				brkps = new HashSet<DSLBreakpoint>();
				BREAKPOINTS.put(breakpoint.getURI(), brkps);
			}
			brkps.add(breakpoint);
		}

		/**
		 * Removes the given {@link DSLBreakpoint}.
		 * 
		 * @param breakpoint
		 *            the {@link DSLBreakpoint}
		 */
		protected void removeBreakpoint(DSLBreakpoint breakpoint) {
			Set<DSLBreakpoint> brkps = BREAKPOINTS.get(breakpoint.getURI());
			if (brkps != null) {
				brkps.remove(breakpoint);
			}
		}

		/**
		 * Notifies Sirius about a change in the given {@link DSLBreakpoint}.
		 * 
		 * @param instructionUri
		 *            the {@link URI} of the instruction to refresh.
		 * @param debugModelID
		 *            the debug model identifier
		 */
		public void notifySirius(URI instructionUri, String debugModelID) {
			Map<String, Set<String>> toRefresh = representationToRefresh.get(debugModelID);
			if (toRefresh != null) {
				for (IEditingSession session : SessionUIManager.INSTANCE.getUISessions()) {
					final TransactionalEditingDomain transactionalEditingDomain = session.getSession()
							.getTransactionalEditingDomain();
					final ResourceSet resourceSet = transactionalEditingDomain.getResourceSet();
					for (DialectEditor editor : session.getEditors()) {
						EObject instruction = resourceSet.getEObject(instructionUri, false);
						if (instruction != null) {
							final DRepresentation representation = editor.getRepresentation();
							final List<DRepresentation> representations = new ArrayList<DRepresentation>();
							final RepresentationDescription description = DialectManager.INSTANCE
									.getDescription(representation);
							final String representationId = description.getName();
							final Set<String> layerIDs = toRefresh.get(representationId);
							if (layerIDs == ANY_LAYER) {
								representations.add(representation);
							} else if (layerIDs != null && representation instanceof DDiagram
									&& isActiveLayer((DDiagram)representation, layerIDs)) {
								representations.add(representation);
							}
							// TODO prevent the editors from getting dirty
							if (representations.size() != 0) {
								final RefreshRepresentationsCommand refresh = new RefreshRepresentationsCommand(
										transactionalEditingDomain, new NullProgressMonitor(),
										representations);
								transactionalEditingDomain.getCommandStack().execute(refresh);
							}
						}
					}
				}
			}
		}

		/**
		 * Tells if any of the given {@link Layer#getName() layer identifier} is active for the given
		 * {@link DDiagram}.
		 * 
		 * @param diagram
		 *            the {@link DDiagram}
		 * @param layerIDs
		 *            the {@link Set} of {@link Layer#getName() layer identifiers}
		 * @return <code>true</code> if any of the given {@link Layer#getName() layer identifier} is active
		 *         for the given {@link DDiagram}, <code>false</code> otherwise
		 */
		private boolean isActiveLayer(DDiagram diagram, Set<String> layerIDs) {
			boolean res = false;

			for (Layer layer : diagram.getActivatedLayers()) {
				if (layerIDs.contains(layer.getName())) {
					res = true;
					break;
				}
			}

			return res;
		}

		/**
		 * Add the given {@link RepresentationDescription#getName() representation identifier} for
		 * {@link DRepresentation} refresh.
		 * 
		 * @param debugModelID
		 *            the debug model identifier
		 * @param representationID
		 *            the {@link RepresentationDescription#getName() representation identifier}
		 */
		public void addRepresentationToRefresh(String debugModelID, String representationID) {
			Map<String, Set<String>> toRefresh = representationToRefresh.get(debugModelID);
			if (toRefresh == null) {
				toRefresh = new HashMap<String, Set<String>>();
				representationToRefresh.put(debugModelID, toRefresh);
			}
			toRefresh.put(representationID, ANY_LAYER);
		}

		/**
		 * Add the given {@link RepresentationDescription#getName() representation identifier} and
		 * {@link Layer#getName() layer identifier} for {@link DRepresentation} refresh.
		 * 
		 * @param debugModelID
		 *            the debug model identifier
		 * @param representationID
		 *            the {@link RepresentationDescription#getName() representation identifier}
		 * @param layerID
		 *            the {@link Layer#getName() layer identifier}
		 */
		public void addRepresentationToRefresh(String debugModelID, String representationID, String layerID) {
			Map<String, Set<String>> toRefresh = representationToRefresh.get(debugModelID);
			if (toRefresh == null) {
				toRefresh = new HashMap<String, Set<String>>();
				representationToRefresh.put(debugModelID, toRefresh);
			}
			Set<String> layerIDs = toRefresh.get(representationID);
			if (layerIDs != ANY_LAYER) {
				if (layerIDs == null) {
					layerIDs = new HashSet<String>();
					toRefresh.put(representationID, layerIDs);
				}
				layerIDs.add(layerID);
			}
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see fr.obeo.dsl.debug.ide.adapter.IDSLCurrentInstructionListener#currentInstructionChanged(String,
		 *      fr.obeo.dsl.debug.StackFrame))
		 */
		public void currentInstructionChanged(String debugModelID, StackFrame frame) {
			final URI instructionURI = EcoreUtil.getURI(frame.getCurrentInstruction());
			final URI lastInstruction = CURRENT_INSTRUCTIONS_PER_FRAME.remove(frame);
			if (lastInstruction != null) {
				notifySirius(lastInstruction, debugModelID);
			}
			CURRENT_INSTRUCTIONS_PER_FRAME.put(frame, instructionURI);
			notifySirius(instructionURI, debugModelID);
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see fr.obeo.dsl.debug.ide.adapter.IDSLCurrentInstructionListener#terminated(java.lang.String,
		 *      fr.obeo.dsl.debug.StackFrame)
		 */
		public void terminated(String debugModelID, StackFrame frame) {
			final URI lastInstruction = CURRENT_INSTRUCTIONS_PER_FRAME.remove(frame);
			if (lastInstruction != null) {
				notifySirius(lastInstruction, debugModelID);
			}
		}

		/**
		 * Gets the current {@link StackFrame}.
		 * 
		 * @return the current {@link StackFrame}
		 */
		public StackFrame getCurrentFrame() {
			return currentFrame;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see fr.obeo.dsl.debug.ide.adapter.IDSLCurrentInstructionListener#setCurrentFrame(java.lang.String,
		 *      fr.obeo.dsl.debug.StackFrame)
		 */
		public void setCurrentFrame(String debugModelID, StackFrame frame) {
			if (currentFrame != frame) {
				currentFrame = frame;
				final URI instructionUri = CURRENT_INSTRUCTIONS_PER_FRAME.get(getCurrentFrame());
				if (instructionUri != null) {
					notifySirius(instructionUri, debugModelID);
				}
			}
		}

		/**
		 * Tells if the given layer id and representation id should be refreshed while debugging the given
		 * debug model id.
		 * 
		 * @param debugModelID
		 *            the debug model id
		 * @param representationId
		 *            the representation id
		 * @param layerID
		 *            the layer id, it can be <code>null</code>
		 * @return <code>true</code> if the given layer id and representation id should be refreshed while
		 *         debugging the given debug model id, <code>false</code> otherwise
		 */
		public boolean isRepresentationToRefresh(String debugModelID, String representationId, String layerID) {
			final boolean res;

			final Map<String, Set<String>> representations = representationToRefresh.get(debugModelID);
			if (representations != null) {
				final Set<String> layerIDs = representations.get(representationId);
				res = layerIDs == ANY_LAYER || layerIDs.contains(layerID);
			} else {
				res = false;
			}

			return res;
		}

	}

	/**
	 * The {@link IBreakpointListener} maintaining breakpoints.
	 */
	public static final BreakpointListener LISTENER = new BreakpointListener();

	/**
	 * {@link Map} of {@link URI} pointing {@link DSLBreakpoint}.
	 */
	private static final Map<URI, Set<DSLBreakpoint>> BREAKPOINTS = new HashMap<URI, Set<DSLBreakpoint>>();

	/**
	 * Current instruction for a given {@link StackFrame}.
	 */
	private static final Map<StackFrame, URI> CURRENT_INSTRUCTIONS_PER_FRAME = new HashMap<StackFrame, URI>();

	/**
	 * Constructor.
	 */
	public AbstractDSLDebuggerServices() {
		for (StringCouple couple : getRepresentationRefreshList()) {
			if (couple.getSecond() != null) {
				LISTENER.addRepresentationToRefresh(getModelIdentifier(), couple.getFirst(), couple
						.getSecond());
			} else {
				LISTENER.addRepresentationToRefresh(getModelIdentifier(), couple.getFirst());
			}
		}
	}

	/**
	 * Gets the {@link List} of {@link StringCouple} representing the
	 * {@link RepresentationDescription#getName() representation identifier} and the {@link Layer#getName()
	 * layer identifier} or <code>null</code> where services from this class are used.
	 * 
	 * @return the {@link List} of {@link StringCouple} representing the
	 *         {@link RepresentationDescription#getName() representation identifier} and the
	 *         {@link Layer#getName() layer identifier} or <code>null</code> where services from this class
	 *         are used
	 */
	protected abstract List<StringCouple> getRepresentationRefreshList();

	/**
	 * Tells if the given {@link EObject instruction} has a breakpoint.
	 * 
	 * @param instruction
	 *            the {@link EObject instruction}
	 * @return <code>true</code> if the given {@link EObject instruction} has a breakpoint, <code>false</code>
	 *         otherwise
	 */
	public boolean hasBreakpoint(EObject instruction) {
		final Set<DSLBreakpoint> brkps = getBreakpoints(instruction);
		return brkps != null && brkps.size() != 0;
	}

	/**
	 * Tells if the given {@link EObject instruction} has an enabled breakpoint.
	 * 
	 * @param instruction
	 *            the {@link EObject instruction}
	 * @return <code>true</code> if the given {@link EObject instruction} has an enabled breakpoint,
	 *         <code>false</code> otherwise
	 */
	public boolean hasEnabledBreakpoint(EObject instruction) {
		boolean res = false;
		final Set<DSLBreakpoint> brkps = getBreakpoints(instruction);

		if (brkps != null && brkps.size() != 0) {
			for (DSLBreakpoint breakpoint : brkps) {
				try {
					if (breakpoint.isEnabled()) {
						res = true;
						break;
					}
				} catch (CoreException e) {
					// ignore
				}
			}
		}

		return res;
	}

	/**
	 * Tells if the given {@link EObject instruction} has an disabled breakpoint.
	 * 
	 * @param instruction
	 *            the {@link EObject instruction}
	 * @return <code>true</code> if the given {@link EObject instruction} has an disabled breakpoint,
	 *         <code>false</code> otherwise
	 */
	public boolean hasDisabledBreakpoint(EObject instruction) {
		boolean res = false;
		final Set<DSLBreakpoint> brkps = getBreakpoints(instruction);

		if (brkps != null && brkps.size() != 0) {
			res = true;
			for (DSLBreakpoint breakpoint : brkps) {
				try {
					if (breakpoint.isEnabled()) {
						res = false;
						break;
					}
				} catch (CoreException e) {
					// ignore
				}
			}
		}

		return res;
	}

	/**
	 * Gets the {@link Set} of {@link DSLBreakpoint} for the
	 * {@link AbstractDSLDebuggerServices#getModelIdentifier() model identifier}.
	 * 
	 * @param instruction
	 *            the instruction to check
	 * @return the {@link Set} of {@link DSLBreakpoint} for the
	 *         {@link AbstractDSLDebuggerServices#getModelIdentifier() model identifier}
	 */
	protected Set<DSLBreakpoint> getBreakpoints(EObject instruction) {
		Set<DSLBreakpoint> res = new HashSet<DSLBreakpoint>();

		Set<DSLBreakpoint> brkps = BREAKPOINTS.get(EcoreUtil.getURI(instruction));
		if (brkps != null) {
			for (DSLBreakpoint breakpoint : brkps) {
				if (breakpoint.getModelIdentifier().equals(getModelIdentifier())) {
					res.add(breakpoint);
				}
			}
		}

		return res;
	}

	/**
	 * Tells if the given {@link EObject instruction} is a currently debugged instruction. A debugged
	 * instruction in this context is an instruction a debug target is suspended on.
	 * 
	 * @param instruction
	 *            the {@link EObject instruction}
	 * @return <code>true</code> if the given {@link EObject instruction} is a currently debugged instruction,
	 *         <code>false</code> otherwise
	 */
	public boolean isCurrentInstruction(EObject instruction) {
		final URI uri = CURRENT_INSTRUCTIONS_PER_FRAME.get(LISTENER.getCurrentFrame());
		return uri != null && uri.equals(EcoreUtil.getURI(instruction));
	}

	/**
	 * Gets the debug model identifier.
	 * 
	 * @return the debug model identifier
	 */
	public abstract String getModelIdentifier();

}
