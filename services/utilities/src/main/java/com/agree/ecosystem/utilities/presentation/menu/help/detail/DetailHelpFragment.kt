package com.agree.ecosystem.utilities.presentation.menu.help.detail

import androidx.navigation.fragment.navArgs
import com.agree.ecosystem.core.utils.base.AgrFragment
import com.agree.ecosystem.core.utils.utility.navigation.navigation
import com.agree.ecosystem.utilities.databinding.FragmentDetailHelpBinding
import com.agree.ecosystem.utilities.domain.reqres.model.help.Answer
import com.agree.ecosystem.utilities.domain.reqres.model.help.Help
import com.agree.ecosystem.utilities.domain.reqres.model.help.Question
import com.agree.ecosystem.utilities.domain.reqres.model.help.Sector
import com.agree.ecosystem.utilities.presentation.navigation.MainNavigation
import com.devbase.presentation.viewpager.setup
import com.devbase.utils.ext.gone
import com.devbase.utils.ext.isNull
import com.google.gson.Gson
import com.google.gson.JsonObject

class DetailHelpFragment : AgrFragment<FragmentDetailHelpBinding>() {

    private val content: Help? by lazy {
        if (args.content.isNotEmpty()) {
            Gson().fromJson(args.content, Help::class.java)
        } else {
            null
        }
    }

    override fun initData() {
        super.initData()
        if (content.isNull()) {
            nav.goToPrevious()
        }
    }

    override fun initUI() {
        super.initUI()
        with(binding) {
            content?.let { help ->
                toolbar.text = help.name
                toolbar.setBackButtonOnClick { requireActivity().onBackPressed() }

                if (help.type == "multi") {
                    val tabContentList: List<Sector> = help.data.asJsonArray.map {
                        it.asJsonObject.convertToSector()
                    }

                    vpHelp.setup {
                        withFragment(this@DetailHelpFragment)
                        withListFragment(
                            tabContentList.map {
                                ContentHelpFragment(it.data)
                            }
                        )
                        bindWithTabLayout(tlHelp) {
                            withListTitles(tabContentList.map { it.name })
                        }
                    }
                } else {
                    tlHelp.gone()

                    val questionList: List<Question> = help.data.asJsonArray.map {
                        it.asJsonObject.convertToQuestion()
                    }

                    vpHelp.setup {
                        withFragment(this@DetailHelpFragment)
                        withListFragment(
                            listOf(
                                ContentHelpFragment(questionList)
                            )
                        )
                    }
                }
            }
        }
    }

    private fun JsonObject.convertToQuestion(): Question {
        return Question(
            data = get("answer").asJsonArray.map {
                it.asJsonObject.convertToAnswer()
            },
            get("question").asString
        )
    }

    private fun JsonObject.convertToAnswer(): Answer {
        return Answer(
            get("desc").asString,
            get("image").asString,
            get("type").asString
        )
    }

    private fun JsonObject.convertToSector(): Sector {
        return Sector(
            get("data").asJsonArray.map {
                it.asJsonObject.convertToQuestion()
            },
            get("name").asString
        )
    }

    private val args: DetailHelpFragmentArgs by navArgs()
    private val nav: MainNavigation by navigation { activity }
}
