package com.g205emmanuelbryanhugo.powerhome.widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import com.g205emmanuelbryanhugo.powerhome.MainActivity;
import com.g205emmanuelbryanhugo.powerhome.R;

public class PowerHomeWidget extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    private void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_power_home);
        
        // Créer un Intent pour ouvrir l'application lors du clic sur le widget
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        views.setOnClickPendingIntent(R.id.widget_consumption, pendingIntent);

        // Mettre à jour les données (à implémenter avec un Service)
        views.setTextViewText(R.id.widget_consumption, "125 kWh");
        views.setTextViewText(R.id.widget_eco_coins, "50 éco-coins");

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
}
