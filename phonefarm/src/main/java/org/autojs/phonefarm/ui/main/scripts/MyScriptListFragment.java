/*
package org.autojs.phonefarm.ui.main.scripts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.stardust.app.GlobalAppContext;
import com.stardust.enhancedfloaty.FloatyService;
import com.stardust.util.IntentUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.autojs.phonefarm.Pref;
import org.autojs.autoxjs.R;
import org.autojs.phonefarm.autojs.AutoJs;
import org.autojs.phonefarm.external.fileprovider.AppFileProvider;
import org.autojs.phonefarm.external.foreground.ForegroundService;
import org.autojs.phonefarm.model.explorer.ExplorerDirPage;
import org.autojs.phonefarm.model.explorer.Explorers;
import org.autojs.phonefarm.model.script.Scripts;
import org.autojs.phonefarm.tool.SimpleObserver;
import org.autojs.phonefarm.ui.common.ScriptOperations;
import org.autojs.phonefarm.ui.explorer.ExplorerView;
import org.autojs.phonefarm.ui.floating.FloatyWindowManger;
import org.autojs.phonefarm.ui.main.FloatingActionMenu;
import org.autojs.phonefarm.ui.main.QueryEvent;
import org.autojs.phonefarm.ui.main.ViewPagerFragment;
import org.autojs.phonefarm.ui.project.ProjectConfigActivity;
import org.autojs.phonefarm.ui.project.ProjectConfigActivity_;
import org.autojs.phonefarm.ui.settings.SettingsActivity_;
import org.autojs.phonefarm.ui.viewmodel.ExplorerItemList;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import io.reactivex.android.schedulers.AndroidSchedulers;

*/
/**
 * Created by Stardust on 2017/3/13.
 *//*

@Deprecated
@EFragment(R.layout.fragment_my_script_list)
public class MyScriptListFragment extends ViewPagerFragment implements FloatingActionMenu.OnFloatingActionButtonClickListener {

    private static final String TAG = "MyScriptListFragment";

    public MyScriptListFragment() {
        super(0);
    }

    @ViewById(R.id.script_file_list)
    ExplorerView mExplorerView;

    private FloatingActionMenu mFloatingActionMenu;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @AfterViews
    void setUpViews() {
        ExplorerItemList.SortConfig sortConfig = ExplorerItemList.SortConfig.from(PreferenceManager.getDefaultSharedPreferences(getContext()));
        mExplorerView.setSortConfig(sortConfig);
        mExplorerView.setExplorer(Explorers.workspace(), ExplorerDirPage.createRoot(Pref.getScriptDirPath()));
        mExplorerView.setOnItemClickListener((view, item) -> {
            if (item.isEditable()) {
                Scripts.INSTANCE.edit(getActivity(), item.toScriptFile());
            } else {
                IntentUtil.viewFile(GlobalAppContext.get(), item.getPath(), AppFileProvider.AUTHORITY);
            }
        });
    }

    @Override
    protected void onFabClick(FloatingActionButton fab) {
        initFloatingActionMenuIfNeeded(fab);
        int[] fabIcons = {
                R.drawable.ic_floating_action_menu_dir,
                R.drawable.ic_floating_action_menu_file,
                R.drawable.ic_floating_action_menu_open,
                R.drawable.ic_project,
                R.drawable.ic_ali_settings,
                R.drawable.ic_ali_exit};
        String[] fabLabs = {"文件夹", "文件", "导入", "项目", "设置", "退出"};
        mFloatingActionMenu.buildFabs(fabIcons,fabLabs);
        mFloatingActionMenu.setOnFloatingActionButtonClickListener(this);
        if (mFloatingActionMenu.isExpanded()) {
            mFloatingActionMenu.collapse();
        } else {
            mFloatingActionMenu.expand();

        }
    }

    private void initFloatingActionMenuIfNeeded(final FloatingActionButton fab) {
        if (mFloatingActionMenu != null)
            return;
        mFloatingActionMenu = getActivity().findViewById(R.id.floating_action_menu);
        mFloatingActionMenu.getState()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleObserver<Boolean>() {
                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull Boolean expanding) {
                        fab.animate()
                                .rotation(expanding ? 45 : 0)
                                .setDuration(300)
                                .start();
                    }
                });
        mFloatingActionMenu.setOnFloatingActionButtonClickListener(this);
    }

    @Override
    public boolean onBackPressed(Activity activity) {
        if (mFloatingActionMenu != null && mFloatingActionMenu.isExpanded()) {
            mFloatingActionMenu.collapse();
            return true;
        }
        if (mExplorerView.canGoBack()) {
            mExplorerView.goBack();
            return true;
        }
        return false;
    }

    @Override
    public void onPageHide() {
        super.onPageHide();
        if (mFloatingActionMenu != null && mFloatingActionMenu.isExpanded()) {
            mFloatingActionMenu.collapse();
        }
    }

    @Subscribe
    public void onQuerySummit(QueryEvent event) {
        if (!isShown()) {
            return;
        }
        if (event == QueryEvent.CLEAR) {
            mExplorerView.setFilter(null);
            return;
        }
        String query = event.getQuery();
        mExplorerView.setFilter((item -> item.getName().contains(query)));
    }

    @Override
    public void onStop() {
        super.onStop();
        mExplorerView.getSortConfig().saveInto(PreferenceManager.getDefaultSharedPreferences(getContext()));
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mFloatingActionMenu != null)
            mFloatingActionMenu.setOnFloatingActionButtonClickListener(null);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(FloatingActionButton button, int pos) {
        if (mExplorerView == null)
            return;
        switch (pos) {
            case 0:
                new ScriptOperations(getContext(), mExplorerView, mExplorerView.getCurrentPage())
                        .newDirectory();
                break;
            case 1:
                new ScriptOperations(getContext(), mExplorerView, mExplorerView.getCurrentPage())
                        .newFile();
                break;
            case 2:
                new ScriptOperations(getContext(), mExplorerView, mExplorerView.getCurrentPage())
                        .importFile();
                break;
            case 3:
                ProjectConfigActivity_.intent(getContext())
                        .extra(ProjectConfigActivity.EXTRA_PARENT_DIRECTORY, mExplorerView.getCurrentPage().getPath())
                        .extra(ProjectConfigActivity.EXTRA_NEW_PROJECT, true)
                        .start();
                break;
            case 4:
                startActivity(new Intent(getContext(), SettingsActivity_.class));
                break;
            case 5:
                requireActivity().finish();
                FloatyWindowManger.hideCircularMenu();
                ForegroundService.stop(requireContext());
                requireActivity().stopService(new Intent(getContext(), FloatyService.class));
                AutoJs.getInstance().getScriptEngineService().stopAll();
                break;
            default:
                break;
        }
    }
}
*/
