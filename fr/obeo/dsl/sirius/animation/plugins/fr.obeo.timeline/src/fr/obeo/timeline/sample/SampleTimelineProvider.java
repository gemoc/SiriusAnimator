/*******************************************************************************
 * Copyright (c) 2013 Obeo. All Rights Reserved.
 *
 * This software and the attached documentation are the exclusive ownership
 * of its authors and was conceded to the profit of Obeo SARL.
 * This software and the attached documentation are protected under the rights
 * of intellectual ownership, including the section "Titre II  Droits des auteurs (Articles L121-1 L123-12)"
 * By installing this software, you acknowledge being aware of this rights and
 * accept them, and as a consequence you must:
 * - be in possession of a valid license of use conceded by Obeo only.
 * - agree that you have read, understood, and will comply with the license terms and conditions.
 * - agree not to do anything that could conflict with intellectual ownership owned by Obeo or its beneficiaries
 * or the authors of this software
 *
 * Should you not agree with these terms, you must stop to use this software and give it back to its legitimate owner.
 *
 *******************************************************************************/
package fr.obeo.timeline.sample;

import fr.obeo.timeline.view.AbstractTimelineProvider;

/**
 * A sample {@link fr.obeo.timeline.view.ITimelineProvider ITimelineProvider}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class SampleTimelineProvider extends AbstractTimelineProvider {

	/**
	 * Populate the timeline asynchronously.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	private final class TimelineRunnable implements Runnable {

		/**
		 * Delay between notifications in miliseconds.
		 */
		private static final int DELAY_MS = 300;

		@Override
		public void run() {
			for (size = 1; size <= possibleSteps.length; ++size) {
				notifyNumberOfChoicesChanged(size);
				final int index = size - 1;
				try {
					Thread.sleep(DELAY_MS);
					final int nbPossibleSteps = 1 + (int)(Math.random() * 5);
					possibleSteps[index] = nbPossibleSteps; // create possible steps
					notifyNumberOfPossibleStepsAtChanged(index, nbPossibleSteps);
					Thread.sleep(DELAY_MS);
					selected[index] = (int)(Math.random() * nbPossibleSteps); // make a selection
					notifyIsSelectedChanged(index, selected[index], true);
					Thread.sleep(DELAY_MS / 2);
					if (index - 1 >= 0) {
						notifyFollowingChanged(index - 1, selected[index - 1], selected[index]);
						Thread.sleep(DELAY_MS / 2);
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * The size of the sample timeline.
	 */
	private static final int SIZE = 100000;

	/**
	 * Sizes of choices.
	 */
	private final int[] possibleSteps;

	/**
	 * Selected possible step.
	 */
	private final int[] selected;

	/**
	 * The size of the timeline.
	 */
	private int size;

	/**
	 * Constructor.
	 */
	public SampleTimelineProvider() {
		possibleSteps = new int[SIZE];
		selected = new int[SIZE];
		for (int i = 0; i < possibleSteps.length; ++i) {
			possibleSteps[i] = -1; // no possible steps
			selected[i] = -1; // no selection
		}

		Runnable runnable = new TimelineRunnable();
		new Thread(runnable).start();
	}

	@Override
	public int getNumberOfChoices() {
		return size;
	}

	@Override
	public int getNumberOfPossibleStepsAt(int index) {
		return possibleSteps[index];
	}

	@Override
	public String getTextAt(int index) {
		return String.valueOf(index);
	}

	@Override
	public Object getAt(int index, int possibleStep) {
		return "something";
	}

	@Override
	public Object getAt(int index) {
		return "something";
	}

	@Override
	public int getSelectedPossibleStep(int index) {
		return selected[index];
	}

	@Override
	public String getTextAt(int index, int possibleStep) {
		return "possible step " + possibleStep;
	}

	@Override
	public int getFollowing(int index, int possibleStep) {
		final int res;

		if (index < getNumberOfChoices() - 1 && possibleStep == selected[index]) {
			res = selected[index + 1];
		} else {
			res = -1;
		}

		return res;
	}

	@Override
	public int getPreceding(int index, int possibleStep) {
		final int res;

		if (index > 0 && possibleStep == selected[index]) {
			res = selected[index - 1];
		} else {
			res = -1;
		}

		return res;
	}

}
