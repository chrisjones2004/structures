package trussoptimizater.Gui.GUIModels;

import java.util.ArrayList;
import java.util.Observable;
import trussoptimizater.Truss.ElementModels.SectionModel;
import trussoptimizater.Truss.Events.SectionEvent;
import trussoptimizater.Truss.Sections.TubularSection;

public class SectionComboBoxModel extends ComboBoxModel<TubularSection> {

	public SectionComboBoxModel(SectionModel model) {
		this(new ArrayList<TubularSection>(), model);
	}

	public SectionComboBoxModel(ArrayList<TubularSection> arrayList,
			SectionModel model) {
		super(arrayList, model);
	}

	@Override
	public void print() {
		System.out.println("\nPrinting Model");
		for (int i = 0; i < elements.size(); i++) {
			System.out.println(elements.get(i).getName());
		}
	}

	@Override
	public Object getElementAt(int i) {
		return elements.get(i).getName();
	}

	@Override
	public void insertElementAt(Object obj, int index) {
		elements.add(index, (TubularSection) obj);
		this.fireIntervalAdded(((TubularSection) obj).getName(), index, index);
	}

	@Override
	public void removeElementAt(int index) {
		this.fireIntervalRemoved(elements.get(index).getName(), index, index);
		elements.remove(index);
	}

	public void update(Observable o, Object arg) {
		SectionEvent se = null;
		ArrayList<TubularSection> sections = null;

		if (!(arg instanceof SectionEvent)) {
			return;
		} else {
			se = (SectionEvent) arg;
			sections = se.getSections();
		}

		switch (se.getEventType()) {
		case SectionEvent.ADD_SECTIONS:
			this.addElements(sections);
			break;
		case SectionEvent.REMOVE_SECTIONS:
			this.removeElements(sections);
			break;
		}

	}

	public Object[] toNameList() {
		String[] sectionNames = new String[elements.size()];
		for (int i = 0; i < elements.size(); i++) {
			sectionNames[i] = elements.get(i).getName();
		}
		return sectionNames;
	}
}
