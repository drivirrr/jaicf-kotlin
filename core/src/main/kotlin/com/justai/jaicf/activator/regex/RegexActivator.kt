package com.justai.jaicf.activator.regex

import com.justai.jaicf.activator.ActivatorFactory
import com.justai.jaicf.activator.BaseActivator
import com.justai.jaicf.api.BotRequest
import com.justai.jaicf.api.hasQuery
import com.justai.jaicf.context.BotContext
import com.justai.jaicf.model.scenario.ScenarioModel
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * This activator handles query requests and activates a state if it contains pattern that matches the request's input.
 * Produces [RegexActivatorContext]
 *
 * @param model dialogue scenario model
 */
class RegexActivator(model: ScenarioModel) : BaseActivator(model) {

    override val name = "regexActivator"

    override fun canHandle(request: BotRequest) = request.hasQuery()

    override fun provideRuleMatcher(botContext: BotContext, request: BotRequest) =
        ruleMatcher<RegexActivationRule> { rule ->
            val pattern = Pattern.compile(rule.regex, Pattern.CASE_INSENSITIVE or Pattern.UNICODE_CASE)
            val matcher = pattern.matcher(request.input)
            if (matcher.matches()) {
                RegexActivatorContext(pattern).also { storeVariables(it, matcher) }
            } else {
                null
            }
        }
        return null
    }

    private fun check(
        path: String,
        query: String,
        currentState: String
    ): Activation? {
        val rules = transitions[path]
        rules?.forEach { r ->
            val m = r.first.matcher(query)
            if (m.matches()) {
                val context = RegexActivatorContext(r.first)
                storeVariables(context, m)
                return Activation(r.second, context)
            }
        }
        return null
    }

    private fun storeVariables(context: RegexActivatorContext, m: Matcher) {
        for (i in 0..m.groupCount()) {
            context.groups.add(m.group(i))
        }

        for (e in getGroupNames(m.pattern())) {
            if (e.value < context.groups.size) {
                context.namedGroups[e.key] = context.groups[e.value]
            }
        }
    }

    private fun getGroupNames(p: Pattern): Map<String, Int> {
        val f = p.javaClass.getDeclaredField("namedGroups")
        f.isAccessible = true
        val v = f.get(p)
        return if (v != null) {
            @Suppress("UNCHECKED_CAST")
            val namedGroups = v as Map<String, Int>
            namedGroups
        } else {
            Collections.emptyMap()
        }
    }

    companion object : ActivatorFactory {
        override fun create(model: ScenarioModel) = RegexActivator(model)
    }
}