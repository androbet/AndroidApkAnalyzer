package sk.styk.martin.apkanalyzer.ui.activity.appdetail.page.feature

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.android.support.AndroidSupportInjection
import sk.styk.martin.apkanalyzer.databinding.FragmentAppDetailFetureBinding
import sk.styk.martin.apkanalyzer.ui.activity.appdetail.base.AppDetailActivity
import sk.styk.martin.apkanalyzer.ui.activity.appdetail.base.AppDetailFragmentViewModel
import sk.styk.martin.apkanalyzer.util.components.toDialog
import sk.styk.martin.apkanalyzer.util.components.toSnackbar
import sk.styk.martin.apkanalyzer.util.provideViewModel
import javax.inject.Inject

class AppFeatureDetailPageFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: AppFeatureDetailFragmentViewModel.Factory

    @Inject
    lateinit var parentViewModelFactory: AppDetailFragmentViewModel.Factory

    private lateinit var binding: FragmentAppDetailFetureBinding

    private lateinit var viewModel: AppFeatureDetailFragmentViewModel

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = provideViewModel {
            viewModelFactory.create(
                    parentViewModelFactory.create(
                            requireNotNull(requireArguments().getParcelable(AppDetailActivity.APP_DETAIL_REQUEST))
                    )
            )
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentAppDetailFetureBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        with(viewModel) {
            openDescription.observe(viewLifecycleOwner, { it.toDialog().show(parentFragmentManager, "descrition_dialog") })
            showSnackbar.observe(viewLifecycleOwner, { it.toSnackbar(requireParentFragment().requireView()).show() })
        }
    }

}