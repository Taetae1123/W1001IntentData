package kr.ac.kumoh.s20200085.w1001intentdata

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import kr.ac.kumoh.s20200085.w1001intentdata.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), OnClickListener {
    //동반 객체
    companion object{
        const val keyName = "image"
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var launcher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGundam.setOnClickListener(this)
        //{
            //Toast.makeText(this, "Gundam", Toast.LENGTH_SHORT).show()
            // intent = Intent(this, ImageActivity::class.java)
            //intent.putExtra(keyName, "gundam")
            //startActivity(intent)
        //}

        binding.btnZaku.setOnClickListener(this)
        //{
            //Toast.makeText(this, "Gundam", Toast.LENGTH_SHORT).show()
        //}

        launcher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){

            if(it.resultCode != RESULT_OK)
                return@registerForActivityResult

            val result = it.data?.getIntExtra(ImageActivity.resultName, ImageActivity.NONE)

            val str = when (result){
                ImageActivity.LIKE -> "좋아요"
                ImageActivity.DISLIKE -> "싫어요"
                else -> ""
            }

            //받아온 것을 처리
            val image = it.data?.getStringExtra(ImageActivity.imageName)
            when(image){
                "gundam" -> binding.btnGundam.text = "건담 ($str)"
                "zaku" -> binding.btnZaku.text = "자쿠 ($str)"
            }
        }

    }

    //인터페이스 구현
    override fun onClick(v: View?) {
        val intent = Intent(this, ImageActivity::class.java)
        val value = when (v?.id){
            binding.btnGundam.id -> "gundam"
            binding.btnZaku.id -> "zaku"
            else -> return
        }
        intent.putExtra(keyName, value)
        //startActivity(intent)
        launcher.launch(intent)
    }
}

//data를 간직할 때 쓰는 거 뷰모델
//이것을 이용하여 몇개의 좋아요를 받았는지 알아볼 수 있다.