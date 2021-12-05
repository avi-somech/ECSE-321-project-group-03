// Generated by view binder compiler. Do not edit!
package ca.mcgill.ecse321.librarymanagement.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import ca.mcgill.ecse321.librarymanagement.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ContentMainBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final RelativeLayout contentMain;

  @NonNull
  public final EditText createTitleDescription;

  @NonNull
  public final EditText createTitleGenre;

  @NonNull
  public final EditText createTitleName;

  @NonNull
  public final EditText createTitleType;

  @NonNull
  public final TextView error;

  private ContentMainBinding(@NonNull RelativeLayout rootView, @NonNull RelativeLayout contentMain,
      @NonNull EditText createTitleDescription, @NonNull EditText createTitleGenre,
      @NonNull EditText createTitleName, @NonNull EditText createTitleType,
      @NonNull TextView error) {
    this.rootView = rootView;
    this.contentMain = contentMain;
    this.createTitleDescription = createTitleDescription;
    this.createTitleGenre = createTitleGenre;
    this.createTitleName = createTitleName;
    this.createTitleType = createTitleType;
    this.error = error;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ContentMainBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ContentMainBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.content_main, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ContentMainBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      RelativeLayout contentMain = (RelativeLayout) rootView;

      id = R.id.createTitle_description;
      EditText createTitleDescription = ViewBindings.findChildViewById(rootView, id);
      if (createTitleDescription == null) {
        break missingId;
      }

      id = R.id.createTitle_genre;
      EditText createTitleGenre = ViewBindings.findChildViewById(rootView, id);
      if (createTitleGenre == null) {
        break missingId;
      }

      id = R.id.createTitle_name;
      EditText createTitleName = ViewBindings.findChildViewById(rootView, id);
      if (createTitleName == null) {
        break missingId;
      }

      id = R.id.createTitle_type;
      EditText createTitleType = ViewBindings.findChildViewById(rootView, id);
      if (createTitleType == null) {
        break missingId;
      }

      id = R.id.error;
      TextView error = ViewBindings.findChildViewById(rootView, id);
      if (error == null) {
        break missingId;
      }

      return new ContentMainBinding((RelativeLayout) rootView, contentMain, createTitleDescription,
          createTitleGenre, createTitleName, createTitleType, error);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}