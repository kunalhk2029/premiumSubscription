package com.app.premiumsubscription.products

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.premiumsubscription.MainActivity
import com.app.premiumsubscription.MainViewModel
import com.app.premiumsubscription.R
import com.app.premiumsubscription.adapters.PremiumSubscriptionAdapter
import com.app.premiumsubscription.adapters.VouchersAdapter
import com.app.premiumsubscription.data.FireBaseDao
import com.app.premiumsubscription.databinding.FragmentSecondBinding
import com.app.premiumsubscription.models.PremiumSubscriptionsModel
import com.app.premiumsubscription.models.VouchersModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductFragment : Fragment(), PremiumSubscriptionAdapter.Interaction,
    VouchersAdapter.Interaction {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

     val viewModel by activityViewModels<MainViewModel>()
    lateinit var premiumSubscriptionAdapter:PremiumSubscriptionAdapter
    lateinit var vouchersAdapter: VouchersAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (viewModel.mainDataSate.value==null){
            viewModel.initShoppingItem()
        }

        premiumSubscriptionAdapter=  PremiumSubscriptionAdapter(this)
        vouchersAdapter=  VouchersAdapter(this)
        binding.premiumrv.apply {
            layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
            adapter=premiumSubscriptionAdapter
        }
        binding.vouchersrv.apply {
            layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
            adapter=vouchersAdapter
        }

        viewModel.mainDataSate.observe(viewLifecycleOwner){
            it.loading.let {
                if (it){
                    binding.errorcd.visibility=View.GONE
                    binding.pb.visibility=View.VISIBLE
                }else{
                    binding.pb.visibility=View.GONE
                }
            }
            it.data?.let {
                it.voucherList?.let {
                    viewModel.setVoucherList(it)
                }
                it.premiumList?.let {
                    viewModel.setPremiumList(it)
                }
            }
            it.errorMessage?.let {
                binding.errorcd.visibility=View.VISIBLE
                binding.errorcd.setOnClickListener {
                    viewModel.initShoppingItem()
                }
            }
        }

        viewModel.mainViewSate.observe(viewLifecycleOwner){
            it.voucherList?.let {
                binding.voucherssubstitle.visibility=View.VISIBLE
                binding.vouchersrv.visibility=View.VISIBLE
                vouchersAdapter.submitList(it)
            }
            it.premiumList?.let {
                binding.premiumsubstitle.visibility=View.VISIBLE
                binding.premiumrv.visibility=View.VISIBLE
                premiumSubscriptionAdapter.submitList(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemSelected(position: Int, item: PremiumSubscriptionsModel) {
        val bundle = Bundle().apply {
            putString("subscriptionName",item.subscriptionName)
            putString("description",item.description)
            putString("duration",item.duration)
            putString("picLink",item.picLink)
            putString("realPrice","Real Price : Rs${item.realprice}")
            putString("ourPrice","Our Price : Rs${item.ourprice}")
        }
        findNavController().navigate(R.id.action_SecondFragment_to_premiumSubscriptionDetailFragment,bundle)
    }

    override fun onItemSelected(position: Int, item: VouchersModel) {
        val bundle = Bundle().apply {
            putString("voucherName",item.voucherName)
            putString("description",item.description)
            putString("picLink",item.picLink)
            putString("realPrice","Real Price : Rs${item.realprice}")
            putString("ourPrice","Our Price : Rs${item.ourprice}")
        }
        findNavController().navigate(R.id.action_SecondFragment_to_vouchersDetailFragment,bundle)
    }
}