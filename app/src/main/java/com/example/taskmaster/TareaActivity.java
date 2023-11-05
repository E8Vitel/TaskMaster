package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.taskmaster.adaptadores.listaTareasAdapter;
import com.example.taskmaster.db.DbTareas;
import com.example.taskmaster.entidades.Tareas;

import java.util.ArrayList;

public class TareaActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout home, settings, about, logout;
    Button btnAgregarTask;
    EditText txtFechaLimite,descripcion,taskName;
    RecyclerView listaTareas;
    ArrayList<Tareas> listaArrayTareas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarea);

        listaTareas = findViewById(R.id.listaTareas);
        listaTareas.setLayoutManager(new LinearLayoutManager(this));

        DbTareas dbTareas = new DbTareas(TareaActivity.this);

        listaArrayTareas = new ArrayList<>();

        listaTareasAdapter adapter = new listaTareasAdapter(dbTareas.mostrarTareas());
        listaTareas.setAdapter(adapter);

        menu = findViewById(R.id.menu);
        home = findViewById(R.id.home);
        about = findViewById(R.id.abouts);
        logout = findViewById(R.id.exit);
        settings = findViewById(R.id.settings);
        drawerLayout = findViewById(R.id.drawerLayout);
        btnAgregarTask = findViewById(R.id.btnAgregarTask);

        // Dentro del método onClick del botón de agregar, por ejemplo

        btnAgregarTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddTaskDialog();
            }
        });



        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDrawer(drawerLayout);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recreate();
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TareaActivity.this, ConfigActivity.class);
                startActivity(intent);
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TareaActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(TareaActivity.this, "Logout", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }
    private void showAddTaskDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.custom_dialog_layout, null);
        builder.setView(view);

        taskName = view.findViewById(R.id.taskName);
        descripcion = view.findViewById(R.id.descripcion);
        txtFechaLimite = view.findViewById(R.id.txtFechaLimite);

        // Configura el AlertDialog
        builder.setTitle("Agregar Tarea")
                .setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DbTareas dbTareas = new DbTareas(TareaActivity.this);
                        long id = dbTareas.insertarTarea(taskName.getText().toString(), descripcion.getText().toString(), txtFechaLimite.getText().toString());

                        if (id > 0) {
                            Toast.makeText(TareaActivity.this, "Registro Existoso", Toast.LENGTH_SHORT).show();
                            limpiar();
                        } else {
                            Toast.makeText(TareaActivity.this, "Registro Fallido", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();

        // Agregar lógica para mostrar el DatePickerDialog al tocar el EditText de fecha
        txtFechaLimite = view.findViewById(R.id.txtFechaLimite);
        txtFechaLimite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
    }
    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // Aquí puedes manejar la fecha seleccionada
                String selectedDate = dayOfMonth + "-" + (month + 1) + "-" + year;
                txtFechaLimite.setText(selectedDate);
            }
        }, 2023, 11, 3); // Ajusta el año, mes y día según sea necesario

        datePickerDialog.show();
    }
    private void limpiar() {
        taskName.setText("");
        descripcion.setText("");
        txtFechaLimite.setText("");
    }
}
