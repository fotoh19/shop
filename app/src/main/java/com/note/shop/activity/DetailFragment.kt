import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.note.shop.R
import com.note.shop.adapter.PicAdapter
import com.note.shop.adapter.SelectedModelAdapter
import com.note.shop.databinding.FragmentDetailBinding
import com.note.shop.helper.ManagmentCart
import com.note.shop.model.ItemsModel
import kotlinx.coroutines.launch

@Suppress("DEPRECATION")
class DetailFragment : Fragment(R.layout.fragment_detail) {
    private var fragmentDetailBinding: FragmentDetailBinding? = null
    private lateinit var item: ItemsModel
    private var numberOreder = 1
    private lateinit var managementCart: ManagmentCart

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentDetailBinding.bind(view)
        fragmentDetailBinding = binding

        managementCart = ManagmentCart(requireContext())

        getBundle()
        initList()
    }

    private fun initList() {
        fragmentDetailBinding?.let { binding ->
            val modelList = ArrayList<String>()
            for (models in item.model) {
                modelList.add(models)
            }
            binding.modelList.adapter = SelectedModelAdapter(modelList)
            binding.modelList.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

            val picList = ArrayList<String>()
            for (imageUrl in item.picUrl) {
                picList.add(imageUrl)
            }

            Glide.with(requireContext())
                .load(picList[0])
                .into(binding.img)

            binding.picList.adapter = PicAdapter(picList) { selectedImageUrl ->
                Glide.with(requireContext())
                    .load(selectedImageUrl)
                    .into(binding.img)
            }
            binding.picList.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getBundle() {
        item = arguments?.getParcelable("object")!!

        fragmentDetailBinding!!.titleTxt.text = item.title
        fragmentDetailBinding!!.descriptinTxt.text = item.description
        fragmentDetailBinding!!.priceTxt.text = "$" + item.price
        fragmentDetailBinding!!.ratingTxt.text = "${item.rating} Rating"

        fragmentDetailBinding!!.addToCartBtn.setOnClickListener {
            item.numberInCart = numberOreder
            viewLifecycleOwner.lifecycleScope.launch {
                try {
                    managementCart.insertItem(item)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        fragmentDetailBinding!!.cartBtn.setOnClickListener {
            findNavController().navigate(R.id.action_detailFragment2_to_cartFragment2)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentDetailBinding = null
    }
}
