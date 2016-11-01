package org.tasks.tasklist;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.ViewGroup;

import com.todoroo.astrid.dao.TaskDao;

import org.tasks.R;
import org.tasks.dialogs.DialogBuilder;
import org.tasks.injection.ForActivity;
import org.tasks.preferences.Preferences;
import org.tasks.ui.CheckBoxes;

import javax.inject.Inject;

import static android.support.v4.content.ContextCompat.getColor;
import static org.tasks.preferences.ResourceResolver.getData;

public class ViewHolderFactory {

    private final int textColorSecondary;
    private final int textColorHint;
    private final int textColorOverdue;
    private final Context context;
    private final CheckBoxes checkBoxes;
    private final TagFormatter tagFormatter;
    private final boolean showFullTaskTitle;
    private final int fontSize;
    private final TaskDao taskDao;
    private final DialogBuilder dialogBuilder;
    private final int minRowHeight;

    @Inject
    public ViewHolderFactory(@ForActivity Context context, Preferences preferences,
                             CheckBoxes checkBoxes, TagFormatter tagFormatter, TaskDao taskDao,
                             DialogBuilder dialogBuilder) {
        this.context = context;
        this.checkBoxes = checkBoxes;
        this.tagFormatter = tagFormatter;
        this.taskDao = taskDao;
        this.dialogBuilder = dialogBuilder;
        textColorSecondary = getData(context, android.R.attr.textColorSecondary);
        textColorHint = getData(context, android.R.attr.textColorTertiary);
        textColorOverdue = getColor(context, R.color.overdue);
        showFullTaskTitle = preferences.getBoolean(R.string.p_fullTaskTitle, false);
        fontSize = preferences.getIntegerFromString(R.string.p_fontSize, 18);
        tagFormatter.updateTagMap();
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        minRowHeight = (int) (metrics.density * 40);
    }

    public ViewHolder newViewHolder(ViewGroup viewGroup, ViewHolder.OnCompletedTaskCallback onCompletedTaskCallback) {
        return new ViewHolder(context, viewGroup, showFullTaskTitle, fontSize, checkBoxes,
                tagFormatter, textColorOverdue, textColorSecondary, textColorHint, taskDao,
                dialogBuilder, onCompletedTaskCallback, minRowHeight);
    }

    public void updateTagMap() {
        tagFormatter.updateTagMap();
    }
}
