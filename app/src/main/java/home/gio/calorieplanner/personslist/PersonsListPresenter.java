package home.gio.calorieplanner.personslist;

import android.content.Context;

import java.lang.ref.WeakReference;
import java.util.List;

import home.gio.calorieplanner.models.Person;


public class PersonsListPresenter implements IPersonsListPresenter {
    WeakReference<IPersonsListView> view;
    PersonsList model;

    public PersonsListPresenter(IPersonsListView view) {
        this.view = new WeakReference<IPersonsListView>(view);
        this.model = new PersonsList();
    }

    private IPersonsListView getView() throws NullPointerException {
        if (view != null)
            return view.get();
        else
            throw new NullPointerException("View is unavailable");
    }

    public void personsListRefill(List<Person> personList, Context context) {
        getView().assignValuePersons(model.personListRefill(personList, context));
    }
}
