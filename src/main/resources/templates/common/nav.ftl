<nav class="navbar-default navbar-static-side" role="navigation">
	<div class="nav-close">
		<i class="fa fa-times-circle"></i>
	</div>
	<div class="sidebar-collapse">
		<ul class="nav" id="side-menu">
			<li class="nav-header">
				<div class="dropdown profile-element">
					<a data-toggle="dropdown" class="dropdown-toggle" href="#"> <span
						class="clear"> <span class="block m-t-xs"
							style="font-size: 20px;"> <i class="fa fa-area-chart"></i>
								<strong class="font-bold">hansholdings</strong>
						</span>
					</span>
					</a>
				</div>
				<div class="logo-element">hansholdings</div>
			</li>
			<li class="hidden-folded padder m-t m-b-sm text-muted text-xs">
				<span class="ng-scope">快捷方式 </span>
			</li>
			<li><a class="J_menuItem" > 
				<i class="fa fa-home"></i> <span class="nav-label">主页</span>
			</a></li>
			<li class="line dk"></li>
			<#list functions["0"] as function>
				<li class="hidden-folded padder m-t m-b-sm text-muted text-xs">
					<span class="ng-scope">${ function.name}</span>
				</li>
				<#list functions[function.id?string("")] as childFun>
					<li>
						<a <#if childFun.child??>href="#"<#else>href="${childFun.url}"</#if>>
							<i class="fa fa-reorder"></i>
							<span class="nav-label">${childFun.name}</span>
							<#if childFun.child??><span class="fa arrow"></span></#if>
						</a>
						<ul class="nav nav-second-level">
							<#list functions[childFun.id?string("")] as cchildFun>
							<li><a class="J_menuItem" href="${cchildFun.url}">${cchildFun.name}</a></li>
							</#list>
						</ul>
					</li>
				</#list>
			</#list>
            <li><a href="logout.html"> <i class="fa fa-sign-out"></i> 退出
		</ul>
	</div>
</nav>