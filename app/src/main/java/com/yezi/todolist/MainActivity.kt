package com.yezi.todolist

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.yezi.todolist.data.Group
import com.yezi.todolist.ui.GroupsViewModel
import com.yezi.todolist.ui.createNewListDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val TAG = "MainActivity"

    private val custom_menu_start_id = 10000;

    private val preinstall_group_ids = arrayListOf(R.id.group_today, R.id.group_important, R.id.group_todo)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        val module = ViewModelProviders.of(this).get(GroupsViewModel::class.java)
        module.load(this).observe(this, Observer<List<Group>> {
            updateGroups(it)
        })

        new_list.setOnClickListener { onNewList() }
    }

    private fun updateGroups(groups:List<Group>) {
        val id_to_remove = arrayListOf<Int>()

        for (i in 0 until nav_view.menu.size()) {
            if (nav_view.menu.getItem(i).itemId !in preinstall_group_ids) {
                id_to_remove.add(nav_view.menu.getItem(i).itemId)
            }
        }

        id_to_remove.forEach { nav_view.menu.removeItem(it) }

        groups.filter { !it.persist }.forEach { nav_view.menu.add(R.id.custom_groups, custom_menu_start_id + it.id, Menu.NONE, it.title) }

    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = when (item.itemId) {
                    R.id.group_today -> Group.PERSIST.TODAY.value
                    R.id.group_important -> Group.PERSIST.IMPORTANT.value
                    R.id.group_todo -> Group.PERSIST.TODO.value
                    else -> item.itemId - custom_menu_start_id
                }
        ViewModelProviders.of(this).get(GroupsViewModel::class.java).getGroup(id)?.let {
            openGroup(it)
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    fun openGroup(group:Group) {

    }

    private fun onNewList() {
        showNewListDialog()
    }

    private fun showNewListDialog() {
        createNewListDialog(this).show()
    }

}
