<#assign base=request.contextPath />
<#assign totalPages = page.totalPages>
<#assign totalElements = page.totalElements>
<#assign number = page.number>
<div class="dataTables_paginate paging_simple_numbers" id="datatable_paginate" style="text-align:right;">
	<ul class="pagination">
	<#if totalPages==0>
	<!-- 无数据时 -->
		<li class="paginate_button previous disabled">
			<a href="#">暂无数据</a>
		</li>
	<#else>
		<!-- 上一页 -->
        <#if number==0>
            <li class="paginate_button previous disabled">
				<a href="#">上一页</a>
			</li>
        <#else>
            <li class="paginate_button previous">
				<a href="javascript:selectQuery(${(number-1)?c});">上一页</a>
			</li>
        </#if>
        <!-- 中间页 -->
        <#if number==0>
        	<#if totalPages <= 10>
        		<#list 1..totalPages as pageIndex>
        			<li class="paginate_button <#if (pageIndex-1)==0>active</#if>">
						<a <#if (pageIndex-1)==0>href="javascript:void(0);"<#else>href="javascript:selectQuery(${(pageIndex-1)?c});"</#if>>${pageIndex?c}</a>
					</li>
                </#list>
            <#else>
            	<#list 1..10 as pageIndex>
            		<li class="paginate_button <#if (pageIndex-1)==0>active</#if>">
						<a <#if (pageIndex-1)==0>href="javascript:void(0);"<#else>href="javascript:selectQuery(${(pageIndex-1)?c});"</#if>>${pageIndex?c}</a>
					</li>
                </#list>
            </#if>
        <#else>
        	<#if totalPages <= 10>
        		<#list 1..totalPages as pageIndex>
	        		<li class="paginate_button <#if (pageIndex-1)==number>active</#if>">
						<a <#if (pageIndex-1)==number>href="javascript:void(0);"<#else>href="javascript:selectQuery(${(pageIndex-1)?c});"</#if>>${pageIndex?c}</a>
					</li>
				</#list>
            <#else>
            	<#if (((number-5) >= 0) && ((number+4) < totalPages))>
                	<#list number-4..number+5 as pageIndex>
                		<li class="paginate_button <#if (pageIndex-1)==number>active</#if>">
							<a <#if (pageIndex-1)==number>href="javascript:void(0);"<#else>href="javascript:selectQuery(${(pageIndex-1)?c});"</#if>>${pageIndex?c}</a>
						</li>
                	</#list>
             	<#else>
                	<#if ((number-4) <= 0)>
                    	<#list 1..10 as pageIndex>
                    		<li class="paginate_button <#if (pageIndex-1)==number>active</#if>">
								<a <#if (pageIndex-1)==number>href="javascript:void(0);"<#else>href="javascript:selectQuery(${(pageIndex-1)?c});"</#if>>${pageIndex?c}</a>
							</li>
                    	</#list>
                    </#if>
                    <#if ((number+4) >= totalPages)>
                    	<#list totalPages-9..totalPages as pageIndex>
                    		<li class="paginate_button <#if (pageIndex-1)==number>active</#if>">
								<a <#if (pageIndex-1)==number>href="javascript:void(0);"<#else>href="javascript:selectQuery(${(pageIndex-1)?c});"</#if>>${pageIndex?c}</a>
							</li>
                    	</#list>
                    </#if>
                </#if>
             </#if>
        </#if>
        <!-- 下一页 -->
        <#if number+1==totalPages>
        	<li class="paginate_button next disabled">
				<a href="#">下一页</a>
			</li>
        <#else>
            <li class="paginate_button next">
				<a href="javascript:selectQuery(${(number+1)?c});">下一页</a>
			</li>
        </#if>
	</#if>
	</ul>
</div>